package perobobbot.launcher.oauth;

import com.google.common.collect.ImmutableMap;
import fpc.tools.micronaut.EagerInit;
import io.micronaut.context.annotation.Factory;
import jakarta.inject.Singleton;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import perobobbot.api.IdentityByLogin;
import perobobbot.api.data.Platform;
import perobobbot.launcher.PerobobbotConfiguration;
import perobobbot.oauth.api.AuthHolder;
import perobobbot.oauth.api.OAuthData;
import perobobbot.oauth.api.OAuthDataFactory;
import perobobbot.service.api.UserIdentityService;

@Factory
@Slf4j
@RequiredArgsConstructor
public class AuthHolderFactory {

    private final @NonNull UserIdentityService userIdentityService;
    private final @NonNull OAuthDataFactory oAuthDataFactory;
    private final @NonNull PerobobbotConfiguration configuration;

    @Singleton
    @EagerInit
    public @NonNull AuthHolder authHolder() {
        final var defaultOAuth = getDefaultOAuthData();
        return new MainAuthHolder(userIdentityService, oAuthDataFactory, defaultOAuth);
    }

    private @NonNull ImmutableMap<Platform, OAuthData> getDefaultOAuthData() {
        final var builder = ImmutableMap.<Platform, OAuthData>builder();
        final var defaultIds = configuration.getOauth().getDefaultIds();
        for (@NonNull Platform platform : defaultIds.keySet()) {
            final var login = defaultIds.get(platform);
            final var id = new IdentityByLogin(platform, login);

            final var identity = userIdentityService.findUserIdentity(id).orElse(null);
            if (identity == null) {
                LOG.warn("Could not find user '{}' on platform '{}'", login, platform.name());
                continue;
            }

            final var oauthData = oAuthDataFactory.create(identity);
            builder.put(oauthData.getPlatform(), oauthData);
        }
        return builder.build();
    }

}
