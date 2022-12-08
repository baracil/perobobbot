package perobobbot.launcher.plugin;

import com.google.common.collect.ImmutableSet;
import jplugman.api.VersionedService;
import jplugman.base.ApplicationBase;
import jplugman.tools.Subscription;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import perobobbot.api.plugin.PerobobbotPlugin;

import java.util.Optional;

@Slf4j
public class PluginApplication extends ApplicationBase<PerobobbotPlugin> {


    public PluginApplication(@NonNull ImmutableSet<VersionedService> versionedServices) {
        super(versionedServices, PerobobbotPlugin.class);
        versionedServices.forEach(s -> {
            LOG.info("Perobobbot service : {} v{}",s.getServiceType().getSimpleName(), s.getMajorVersion());
        });
    }

    @Override
    protected Optional<Subscription> handleService(@NonNull PerobobbotPlugin service) {
        LOG.info("Handling new plugin {}", service.getName());
        try {
            service.onPluginStarted();
            return Optional.of(service::onPluginStopped);
        } catch (Throwable t) {
            LOG.error("Error while handling plugin '{}'",service.getName(),t);
            return Optional.empty();
        }
    }

}
