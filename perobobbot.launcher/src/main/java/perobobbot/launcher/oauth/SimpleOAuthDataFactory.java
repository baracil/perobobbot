package perobobbot.launcher.oauth;

import fpc.tools.lang.Instants;
import jakarta.inject.Singleton;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.api.Identification;
import perobobbot.api.oauth.OAuthData;
import perobobbot.api.oauth.OAuthDataFactory;
import perobobbot.oauth.OAuthManager;
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
    public @NonNull OAuthData create(@NonNull Identification identification, @NonNull String login) {
        return new SimpleOAuthData(identification,login,userTokenService,applicationService,oAuthManager,instants);
    }
}
