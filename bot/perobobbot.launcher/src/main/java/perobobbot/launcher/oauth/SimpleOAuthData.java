package perobobbot.launcher.oauth;

import fpc.tools.lang.Instants;
import fpc.tools.lang.Secret;
import lombok.Getter;
import lombok.NonNull;
import lombok.Synchronized;
import perobobbot.api.Identity;
import perobobbot.api.UserInfo;
import perobobbot.api.data.*;
import perobobbot.oauth.api.OAuthAccessMode;
import perobobbot.oauth.api.OAuthData;
import perobobbot.oauth.api.OAuthManager;
import perobobbot.service.api.ApplicationService;
import perobobbot.service.api.UserTokenService;

public class SimpleOAuthData implements OAuthData {

    @Getter
    private final @NonNull Identity identity;
    @Getter
    private final @NonNull String login;
    private final @NonNull UserTokenService userTokenService;
    private final @NonNull ApplicationService applicationService;
    private final @NonNull OAuthManager oAuthManager;
    private final @NonNull Instants instants;

    public SimpleOAuthData(@NonNull UserInfo userInfo, @NonNull UserTokenService userTokenService, @NonNull ApplicationService applicationService, @NonNull OAuthManager oAuthManager, @NonNull Instants instants) {
        this.identity = userInfo.identity();
        this.login = userInfo.login();
        this.userTokenService = userTokenService;
        this.applicationService = applicationService;
        this.oAuthManager = oAuthManager;
        this.instants = instants;
    }

    @Override
    public @NonNull Platform getPlatform() {
        return identity.platform();
    }

    @Override
    public @NonNull String getClientId() {
        return applicationService.getApplicationClientId(identity.platform());
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
    public @NonNull String getUserId() {
        return identity.userId();
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
        final var userToken = userTokenService.findUserToken(identity.platform(), identity.userId()).orElse(null);
        if (userToken != null) {
            return userToken;
        }
        return getApplicationToken();
    }

    @Synchronized
    private @NonNull UserToken.Decrypted getUserToken() {
        return userTokenService.getUserToken(identity.platform(), identity.userId());
    }

    @Synchronized
    private @NonNull ApplicationToken.Decrypted getApplicationToken() {
        final var platform = identity.platform();
        final var token = applicationService.findApplicationToken(platform).orElse(null);
        if (token != null) {
            return token;
        }
        oAuthManager.getAppToken(platform);
        return applicationService.getApplicationToken(identity.platform());
    }

    private void refreshForBoth() {
        userTokenService.findUserRefreshToken(identity.platform(), identity.userId())
                        .ifPresentOrElse(this::refreshUserToken, this::refreshApplicationToken);
    }

    @Synchronized
    private void refreshUserToken() {
        final var platform = identity.platform();
        final var token = userTokenService.findUserRefreshToken(platform, identity.userId()).orElse(null);
        if (token == null) {
            return;
        }
        refreshUserToken(token);
    }

    private void refreshUserToken(@NonNull RefreshTokenInfo<Secret> token) {
        if (token.expirationInstant().isAfter(instants.now())) {
            return;
        }

        final var refreshed = oAuthManager.refreshUserToken(token.platform(), token.refreshToken());
        userTokenService.setUserToken(refreshed);
    }


    @Synchronized
    private void refreshApplicationToken() {
        final var platform = identity.platform();
        final var token = applicationService.findApplicationToken(platform).orElse(null);
        if (token != null && token.expirationInstant().isAfter(instants.now())) {
            return;
        }
        final var newToken = oAuthManager.getAppToken(platform);
        applicationService.saveApplicationToken(newToken);
    }
}
