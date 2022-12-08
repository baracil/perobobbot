package perobobbot.launcher.oauth;

import fpc.tools.lang.Instants;
import jakarta.inject.Singleton;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.api.UserInfo;
import perobobbot.oauth.api.OAuthData;
import perobobbot.oauth.api.OAuthDataFactory;
import perobobbot.oauth.api.OAuthManager;
import perobobbot.service.api.ApplicationService;
import perobobbot.service.api.UserTokenService;

@RequiredArgsConstructor
@Singleton
public class SimpleOAuthDataFactory implements OAuthDataFactory {

    private final @NonNull UserTokenService userTokenService;
    private final @NonNull ApplicationService applicationService;
    private final @NonNull OAuthManager oAuthManager;
    private final @NonNull Instants instants;

    @Override
    public @NonNull OAuthData create(@NonNull UserInfo userInfo) {
        return new SimpleOAuthData(userInfo, userTokenService, applicationService, oAuthManager, instants);
    }
}
