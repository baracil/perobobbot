package perobobbot.launcher.oauth;

import fpc.tools.lang.Instants;
import fpc.tools.lang.Secret;
import lombok.Getter;
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
    private final Identity identity;
    @Getter
    private final String login;
    private final UserTokenService userTokenService;
    private final ApplicationService applicationService;
    private final OAuthManager oAuthManager;
    private final Instants instants;

    public SimpleOAuthData(UserInfo userInfo, UserTokenService userTokenService, ApplicationService applicationService, OAuthManager oAuthManager, Instants instants) {
        this.identity = userInfo.identity();
        this.login = userInfo.login();
        this.userTokenService = userTokenService;
        this.applicationService = applicationService;
        this.oAuthManager = oAuthManager;
        this.instants = instants;
    }

    @Override
    public Platform getPlatform() {
        return identity.platform();
    }

    @Override
    public String getClientId() {
        return applicationService.getApplicationClientId(identity.platform());
    }

    @Override
    public void refresh(OAuthAccessMode oAuthAccessMode) {
        switch (oAuthAccessMode) {
            case USER_ONLY -> refreshUserToken();
            case APP_ONLY -> refreshApplicationToken();
            case BOTH_ACCEPTED -> refreshForBoth();
        }
    }

    @Override
    public String getUserId() {
        return identity.userId();
    }

    @Override
    public Secret getAccessToken(OAuthAccessMode oAuthAccessMode) {
        final AccessTokenProvider provider = switch (oAuthAccessMode) {
            case USER_ONLY -> getUserToken();
            case APP_ONLY -> getApplicationToken();
            case BOTH_ACCEPTED -> getForBothAccepted();
        };
        return provider.accessToken();
    }

    @Synchronized
    private AccessTokenProvider getForBothAccepted() {
        final var userToken = userTokenService.findUserToken(identity.platform(), identity.userId()).orElse(null);
        if (userToken != null) {
            return userToken;
        }
        return getApplicationToken();
    }

    @Synchronized
    private UserToken.Decrypted getUserToken() {
        return userTokenService.getUserToken(identity.platform(), identity.userId());
    }

    @Synchronized
    private ApplicationToken.Decrypted getApplicationToken() {
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

    private void refreshUserToken(RefreshTokenInfo<Secret> token) {
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
