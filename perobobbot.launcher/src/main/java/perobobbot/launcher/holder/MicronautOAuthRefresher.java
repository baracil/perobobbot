package perobobbot.launcher.holder;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.api.data.Platform;
import perobobbot.api.data.view.ApplicationToken;
import perobobbot.api.data.view.UserToken;
import perobobbot.api.oauth.AuthData;
import perobobbot.api.oauth.OAuthAccessMode;
import perobobbot.api.oauth.OAuthRefresher;
import perobobbot.oauth.OAuthManager;
import perobobbot.service.api.ApplicationService;
import perobobbot.service.api.UserTokenService;

import java.util.Optional;

@RequiredArgsConstructor
public class MicronautOAuthRefresher implements OAuthRefresher {

    private final @NonNull OAuthManager oAuthManager;
    private final @NonNull UserTokenService userTokenService;
    private final @NonNull ApplicationService applicationService;

    @Override
    public @NonNull AuthData prepare(@NonNull AuthData authData, @NonNull OAuthAccessMode mode) {
        return switch (mode) {
            case USER_ONLY -> authData;
            case APP_ONLY -> prepareForAppOnly(authData);
            case BOTH -> prepareForBoth(authData);
        };
    }

    @Override
    public @NonNull AuthData refresh(@NonNull AuthData authData, @NonNull OAuthAccessMode mode) {
        return switch (mode) {
            case USER_ONLY -> refreshUserOnly(authData);
            case APP_ONLY -> refreshAppOnly(authData);
            case BOTH -> refreshBoth(authData);
        };
    }



    private @NonNull AuthData prepareForAppOnly(AuthData authData) {
        if (authData.getApplicationToken().isPresent()) {
            return authData;
        }
        final var token = getAppToken(authData.getPlatform());
        return authData.withApplicationToken(token);
    }

    private @NonNull AuthData prepareForBoth(AuthData authData) {
        if (authData.getApplicationToken().isPresent() || authData.getUserToken().isPresent()) {
            return authData;
        }
        final var token = getAppToken(authData.getPlatform());
        return authData.withApplicationToken(token);
    }

    private AuthData refreshUserOnly(AuthData authData) {
        return getRefreshedUserToken(authData).map(authData::withUserToken)
                                              .orElse(authData);
    }

    private AuthData refreshAppOnly(AuthData authData) {
        final var appToken = getAppToken(authData.getPlatform());
        return authData.withApplicationToken(appToken);
    }

    private AuthData refreshBoth(AuthData authData) {
        return getRefreshedUserToken(authData)
                .map(authData::withUserToken)
                .orElseGet(() -> refreshAppOnly(authData));
    }

    private @NonNull Optional<UserToken.Decrypted> getRefreshedUserToken(@NonNull AuthData authData) {
        return authData.getUserToken().map(this::refreshToken);
    }


    private @NonNull ApplicationToken.Decrypted getAppToken(@NonNull Platform platform) {
        final var appToken = oAuthManager.getAppToken(platform);
        applicationService.saveApplicationToken(appToken);
        return appToken;
    }

    private @NonNull UserToken.Decrypted refreshToken(@NonNull UserToken.Decrypted userToken) {
        final var token = oAuthManager.refreshUserToken(userToken.platform(),userToken.refreshToken());
        userTokenService.setUserToken(token);
        return token;
    }

}
