package perobobbot.launcher.oauth;

import fpc.tools.lang.Instants;
import fpc.tools.lang.Secret;
import lombok.Getter;
import lombok.NonNull;
import lombok.Synchronized;
import perobobbot.api.Identification;
import perobobbot.api.data.view.AccessTokenProvider;
import perobobbot.api.data.view.ApplicationToken;
import perobobbot.api.data.view.UserIdentity;
import perobobbot.api.data.view.UserToken;
import perobobbot.api.oauth.OAuthAccessMode;
import perobobbot.api.oauth.OAuthData;
import perobobbot.oauth.OAuthManager;
import perobobbot.service.api.ApplicationService;
import perobobbot.service.api.RefreshTokenInfo;
import perobobbot.service.api.UserTokenService;

public class SimpleOAuthData implements OAuthData {

    @Getter
    private final @NonNull Identification identification;
    @Getter
    private final @NonNull String login;
    private final @NonNull UserTokenService userTokenService;
    private final @NonNull ApplicationService applicationService;
    private final @NonNull OAuthManager oAuthManager;
    private final @NonNull Instants instants;

    public SimpleOAuthData(@NonNull UserIdentity userIdentity, @NonNull UserTokenService userTokenService, @NonNull ApplicationService applicationService, @NonNull OAuthManager oAuthManager, @NonNull Instants instants) {
        this.identification = userIdentity.identification();
        this.login = userIdentity.login();
        this.userTokenService = userTokenService;
        this.applicationService = applicationService;
        this.oAuthManager = oAuthManager;
        this.instants = instants;
    }

    @Override
    public @NonNull String getClientId() {
        return applicationService.getApplicationClientId(identification.platform());
    }

    @Override
    public void refresh(@NonNull OAuthAccessMode oAuthAccessMode) {
        switch (oAuthAccessMode) {
            case USER_ONLY -> refreshUserToken();
            case APP_ONLY -> refreshApplicationToken();
            case BOTH_ACCEPTED -> refreshForBoth();
        }
    }

    @Override
    public @NonNull Secret getAccessToken(@NonNull OAuthAccessMode oAuthAccessMode) {
        final AccessTokenProvider provider = switch (oAuthAccessMode) {
            case USER_ONLY -> getUserToken();
            case APP_ONLY -> getApplicationToken();
            case BOTH_ACCEPTED -> getForBothAccepted();
        };
        return provider.accessToken();
    }

    @Synchronized
    private @NonNull AccessTokenProvider getForBothAccepted() {
        final var userToken = userTokenService.findUserToken(identification.platform(), identification.userId()).orElse(null);
        if (userToken != null) {
            return userToken;
        }
        return getApplicationToken();
    }

    @Synchronized
    private @NonNull UserToken.Decrypted getUserToken() {
        return userTokenService.getUserToken(identification.platform(), identification.userId());
    }

    @Synchronized
    private @NonNull ApplicationToken.Decrypted getApplicationToken() {
        final var platform = identification.platform();
        final var token = applicationService.findApplicationToken(platform).orElse(null);
        if (token != null) {
            return token;
        }
        oAuthManager.getAppToken(platform);
        return applicationService.getApplicationToken(identification.platform());
    }

    private void refreshForBoth() {
        userTokenService.findUserRefreshToken(identification.platform(), identification.userId())
                        .ifPresentOrElse(this::refreshUserToken, this::refreshApplicationToken);
    }

    @Synchronized
    private void refreshUserToken() {
        final var platform = identification.platform();
        final var token = userTokenService.findUserRefreshToken(platform, identification.userId()).orElse(null);
        if (token == null) {
            return;
        }
        refreshUserToken(token);
    }

    private void refreshUserToken(@NonNull RefreshTokenInfo<Secret> token) {
//        if (token.expirationInstant().isAfter(instants.now())) {
//            return;
//        }

        final var refreshed = oAuthManager.refreshUserToken(token.platform(), token.refreshToken());
        userTokenService.setUserToken(refreshed);
    }


    @Synchronized
    private void refreshApplicationToken() {
        final var platform = identification.platform();
        final var token = applicationService.findApplicationToken(platform).orElse(null);
        if (token != null && token.expirationInstant().isAfter(instants.now())) {
            return;
        }
        final var newToken = oAuthManager.getAppToken(platform);
        applicationService.saveApplicationToken(newToken);
    }
}
