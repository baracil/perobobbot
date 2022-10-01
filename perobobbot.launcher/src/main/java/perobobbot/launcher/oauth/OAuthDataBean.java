package perobobbot.launcher.oauth;

import fpc.tools.lang.Instants;
import fpc.tools.lang.Secret;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import perobobbot.api.data.view.AccessTokenProvider;
import perobobbot.api.data.view.ApplicationToken;
import perobobbot.api.data.view.UserIdentity;
import perobobbot.api.data.view.UserToken;
import perobobbot.api.oauth.AData;
import perobobbot.api.oauth.OAuthAccessMode;
import perobobbot.oauth.OAuthManager;
import perobobbot.service.api.ApplicationService;
import perobobbot.service.api.UserTokenService;

@RequiredArgsConstructor
public class OAuthDataBean implements AData {

    @Getter
    private final @NonNull UserIdentity userIdentity;
    private final @NonNull UserTokenService userTokenService;
    private final @NonNull ApplicationService applicationService;
    private final @NonNull OAuthManager oAuthManager;
    private final @NonNull Instants instants;

    @Override
    public @NonNull String getClientId() {
        return applicationService.getApplicationClientId(userIdentity.platform());
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
        final var userToken = userTokenService.findUserToken(userIdentity.platform()).orElse(null);
        if (userToken != null) {
            return userToken;
        }
        return getApplicationToken();
    }

    @Synchronized
    private @NonNull UserToken.Decrypted getUserToken() {
        return userTokenService.getUserToken(userIdentity.platform());
    }

    @Synchronized
    private @NonNull ApplicationToken.Decrypted getApplicationToken() {
        final var platform = userIdentity.platform();
        final var token = applicationService.findApplicationToken(platform).orElse(null);
        if (token != null) {
            return token;
        }
        oAuthManager.getAppToken(platform);
        return applicationService.getApplicationToken(userIdentity.platform());
    }

    private void refreshForBoth() {
        final var userToken = userTokenService.findUserToken(userIdentity.platform()).orElse(null);
        if (userToken != null) {
            refreshUserToken();
        } else {
            refreshApplicationToken();
        }
    }

    @Synchronized
    private void refreshUserToken() {
        final var platform = userIdentity.platform();
        final var token = userTokenService.findUserToken(platform).orElse(null);
        if (token == null) {
            return;
        }
        if (token.expirationInstant().isAfter(instants.now())) {
            return;
        }

        final var refreshed = oAuthManager.refreshUserToken(platform, token.refreshToken());
        userTokenService.setUserToken(refreshed);
    }

    @Synchronized
    private void refreshApplicationToken() {
        final var platform = userIdentity.platform();
        final var token = applicationService.findApplicationToken(platform).orElse(null);
        if (token != null && token.expirationInstant().isAfter(instants.now())) {
            return;
        }
        final var newToken = oAuthManager.getAppToken(platform);
        applicationService.saveApplicationToken(newToken);
    }
}
