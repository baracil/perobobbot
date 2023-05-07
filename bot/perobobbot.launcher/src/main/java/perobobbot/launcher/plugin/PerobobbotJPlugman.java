package perobobbot.launcher.plugin;

import io.micronaut.context.ApplicationContext;
import io.micronaut.context.annotation.Requires;
import io.micronaut.context.event.ApplicationEventListener;
import io.micronaut.discovery.event.ServiceReadyEvent;
import jakarta.annotation.PreDestroy;
import jakarta.inject.Singleton;
import jplugman.base.JPlugman;
import lombok.extern.slf4j.Slf4j;
import perobobbot.launcher.PerobobbotConfiguration;

@Singleton
@Requires(bean = PerobobbotConfiguration.class)
@Slf4j
public class PerobobbotJPlugman implements ApplicationEventListener<ServiceReadyEvent> {

    private final JPlugman jPlugman;

    public PerobobbotJPlugman(ApplicationContext applicationContext,
                              PerobobbotConfiguration configuration) {
        final var application = new PluginApplication(VersionedServiceLister.list(applicationContext));
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
