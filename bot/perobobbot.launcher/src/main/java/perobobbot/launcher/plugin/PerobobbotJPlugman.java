package perobobbot.launcher.plugin;

import io.micronaut.context.ApplicationContext;
import io.micronaut.context.annotation.Requires;
import io.micronaut.context.event.ApplicationEventListener;
import io.micronaut.discovery.event.ServiceReadyEvent;
import jakarta.inject.Singleton;
import jplugman.base.JPlugman;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import perobobbot.launcher.PerobobbotConfiguration;
import perobobbot.oauth.api.OAuthManager;

import javax.annotation.PreDestroy;

@Singleton
@Requires(bean = PerobobbotConfiguration.class)
@Slf4j
public class PerobobbotJPlugman implements ApplicationEventListener<ServiceReadyEvent> {

    private final @NonNull JPlugman jPlugman;

    public PerobobbotJPlugman(@NonNull ApplicationContext applicationContext,
                              @NonNull PerobobbotConfiguration configuration) {
        final var application = new PluginApplication(VersionedServiceLister.list(applicationContext), applicationContext.getBean(OAuthManager.class));
        this.jPlugman = new JPlugman(application, configuration.getPlugin().getFolder());
    }

    @PreDestroy
    public void stop() throws InterruptedException {
        LOG.info("Stopping plugin manager");
        jPlugman.stop();
    }

    @Override
    public void onApplicationEvent(ServiceReadyEvent event) {
        LOG.info("Starting plugin manager");
        jPlugman.start();
    }
}
