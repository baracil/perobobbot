package perobobbot.bridge;

import fpc.tools.lang.MapTool;
import io.micronaut.retry.annotation.Fallback;
import jakarta.inject.Singleton;
import perobobbot.api.Identity;
import perobobbot.api.PerobobbotException;
import perobobbot.api.data.Platform;
import perobobbot.api.data.PlatformUserInfoProvider;
import perobobbot.api.data.UserIdentity;
import perobobbot.api.data.UserIdentityProvider;
import perobobbot.service.api.UserIdentityService;

import java.util.List;
import java.util.Map;

@Singleton
@Fallback
public class BridgeUserIdentityProvider implements UserIdentityProvider {

    private final UserIdentityService userIdentityService;
    private final Map<Platform, PlatformUserInfoProvider> providerPerPlatforms;

    public BridgeUserIdentityProvider(
            UserIdentityService userIdentityService,
            List<PlatformUserInfoProvider> platformUserInfoProvider) {
        this.userIdentityService = userIdentityService;
        this.providerPerPlatforms = platformUserInfoProvider.stream().collect(MapTool.collector(PlatformUserInfoProvider::getPlatform));
    }

    @Override
    public UserIdentity getUserIdentity(Identity identity) {
        final var userInfo = userIdentityService.findUserIdentity(identity);
        return userInfo.orElseGet(() -> getAndSaveUserIdentity(identity));
    }

    private UserIdentity getAndSaveUserIdentity(Identity identity) {
        final var userInfo = getUserIdentityProviderForPlatform(identity.platform()).getUserInfo(identity.userId());
        return userIdentityService.saveUserInfo(userInfo);
    }

    private PlatformUserInfoProvider getUserIdentityProviderForPlatform(Platform platform) {
        final var provider = providerPerPlatforms.get(platform);
        if (provider == null) {
            throw new PerobobbotException("No PlatformUserIdentityProvider for platform : "+platform.name());
        }
        return provider;
    }
}
