package perobobbot.launcher.eventsub;

import fpc.tools.lang.MapTool;
import io.micronaut.retry.annotation.Fallback;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import perobobbot.api.PlatformSubscriptionManager;
import perobobbot.api.SubscriptionManager;
import perobobbot.api.data.Platform;
import perobobbot.api.event.PlatformSync;
import perobobbot.bus.api.Bus;
import perobobbot.bus.api.Producer;

import java.util.List;
import java.util.Map;

@Slf4j
@Singleton
@Fallback
public class PeroSubscriptionManager implements SubscriptionManager {

    private final Map<Platform, PlatformSubscriptionManager> managers;

    private final Producer producer;

    public PeroSubscriptionManager(Bus bus,
                                   List<PlatformSubscriptionManager> managers) {
        this.producer = bus.createProducer(SYSTEM_SUBSCRIPTION_TOPIC);
        this.managers = managers.stream()
                                .collect(MapTool.collector(PlatformSubscriptionManager::getPlatform));
    }

    @PostConstruct
    public void onDestroy() {
        producer.close();
    }

    @Override
    public String getCallbackUrl(Platform platform) {
        final var manager = managers.get(platform);
        if (manager == null) {
            throw new IllegalArgumentException("'" + platform + "' is not managed");
        }
        return manager.getCallbackUrl();
    }

    @Override
    public void requestSynchronization(Platform platform) {
        producer.send(PlatformSync.forPlatform(platform));
    }

    @Override
    public void requestSynchronizationForAllPlatforms() {
        producer.send(PlatformSync.forAllPlatforms());
    }

}
