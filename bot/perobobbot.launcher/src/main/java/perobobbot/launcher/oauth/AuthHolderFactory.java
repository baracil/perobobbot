package perobobbot.launcher.oauth;

import fpc.tools.lang.MapTool;
import fpc.tools.micronaut.EagerInit;
import io.micronaut.context.annotation.Factory;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import perobobbot.api.IdentityByLogin;
import perobobbot.api.data.Platform;
import perobobbot.launcher.PerobobbotConfiguration;
import perobobbot.oauth.api.AuthHolder;
import perobobbot.oauth.api.OAuthData;
import perobobbot.oauth.api.OAuthDataFactory;
import perobobbot.service.api.UserIdentityService;

import java.util.Collections;
import java.util.Map;

@Factory
@Slf4j
@RequiredArgsConstructor
public class AuthHolderFactory {

    private final UserIdentityService userIdentityService;
    private final OAuthDataFactory oAuthDataFactory;
    private final PerobobbotConfiguration configuration;

    @Singleton
    @EagerInit
    public AuthHolder authHolder() {
        final var defaultOAuth = getDefaultOAuthData();
        return new MainAuthHolder(userIdentityService, oAuthDataFactory, defaultOAuth);
    }

    private Map<Platform, OAuthData> getDefaultOAuthData() {
        final var result = MapTool.<Platform,OAuthData>hashMap();
        final var defaultIds = configuration.getOauth().getDefaultIds();
        for (Platform platform : defaultIds.keySet()) {
            final var login = defaultIds.get(platform);
            final var id = new IdentityByLogin(platform, login);

            final var identity = userIdentityService.findUserIdentity(id).orElse(null);
            if (identity == null) {
                LOG.warn("Could not find user '{}' on platform '{}'", login, platform.name());
                continue;
            }

            final var oauthData = oAuthDataFactory.create(identity.toUserInfo());
            result.put(oauthData.getPlatform(), oauthData);
        }
        return Collections.unmodifiableMap(result);
    }

}
