package perobobbot.launcher.eventsub;

import com.google.common.collect.ImmutableMap;
import io.micronaut.retry.annotation.Fallback;
import jakarta.inject.Singleton;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import perobobbot.api.PlatformSubscriptionManager;
import perobobbot.api.SubscriptionManager;
import perobobbot.api.data.Platform;
import perobobbot.api.event.PlatformSync;
import perobobbot.bus.api.Bus;
import perobobbot.bus.api.Producer;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.function.Function;

@Slf4j
@Singleton
@Fallback
public class PeroSubscriptionManager implements SubscriptionManager {

    private final @NonNull ImmutableMap<Platform, PlatformSubscriptionManager> managers;

    private final Producer producer;

    public PeroSubscriptionManager(@NonNull Bus bus,
                                   @NonNull List<PlatformSubscriptionManager> managers) {
        this.producer = bus.createProducer(SYSTEM_SUBSCRIPTION_TOPIC);
        this.managers = managers.stream()
                                .collect(ImmutableMap.toImmutableMap(PlatformSubscriptionManager::getPlatform, Function.identity()));
    }

    @PostConstruct
    public void onDestroy() {
        producer.close();
    }

    @Override
    public @NonNull String getCallbackUrl(@NonNull Platform platform) {
        final var manager = managers.get(platform);
        if (manager == null) {
            throw new IllegalArgumentException("'" + platform + "' is not managed");
        }
        return manager.getCallbackUrl();
    }

    @Override
    public void requestSynchronization(@NonNull Platform platform) {
        producer.send(PlatformSync.forPlatform(platform));
    }

    @Override
    public void requestSynchronizationForAllPlatforms() {
        producer.send(PlatformSync.forAllPlatforms());
    }

}
