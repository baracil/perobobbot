package perobobbot.launcher.plugin;

import com.google.common.collect.ImmutableSet;
import jplugman.api.VersionedService;
import jplugman.base.ApplicationBase;
import jplugman.tools.Subscription;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import perobobbot.api.plugin.PerobobbotPlugin;
import perobobbot.api.plugin.PerobobbotPluginData;
import perobobbot.api.plugin.PlatformOAuthPlugin;
import perobobbot.oauth.OAuthManager;

import java.util.Objects;
import java.util.Optional;

@Slf4j
public class PluginApplication extends ApplicationBase<PerobobbotPlugin> {

    private final @NonNull OAuthManager oAuthManager;

    public PluginApplication(
            @NonNull ImmutableSet<VersionedService> versionedServices,
            @NonNull OAuthManager oAuthManager) {
        super(versionedServices, PerobobbotPlugin.class);
        this.oAuthManager = oAuthManager;
    }

    @Override
    protected Optional<Subscription> handleService(@NonNull PerobobbotPlugin service) {
        LOG.info("Handling new plugin {}", service.getName());
        return service.getPluginData()
                      .stream()
                      .map(this::handleServiceData)
                      .filter(Objects::nonNull)
                      .reduce(fpc.tools.lang.Subscription::then)
                      .map(s -> s::unsubscribe);
    }

    private fpc.tools.lang.Subscription handleServiceData(PerobobbotPluginData data) {
        if (data instanceof PlatformOAuthPlugin platformOAuthPlugin) {
            return oAuthManager.register(platformOAuthPlugin.platformOAuth());
        }
        return null;
    }
}
