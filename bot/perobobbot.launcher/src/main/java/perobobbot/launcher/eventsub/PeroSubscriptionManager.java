package perobobbot.launcher.eventsub;

import com.google.common.collect.ImmutableMap;
import io.micronaut.retry.annotation.Fallback;
import jakarta.inject.Singleton;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import perobobbot.api.PlatformSubscriptionManager;
import perobobbot.api.SubscriptionManager;
import perobobbot.api.bus.Bus;
import perobobbot.api.bus.PlatformSync;
import perobobbot.api.data.Platform;

import java.util.List;
import java.util.function.Function;

@Slf4j
@Singleton
@Fallback
public class PeroSubscriptionManager implements SubscriptionManager {

    private final @NonNull Bus bus;
    private final @NonNull ImmutableMap<Platform, PlatformSubscriptionManager> managers;

    public PeroSubscriptionManager(@NonNull Bus bus,
                                   @NonNull List<PlatformSubscriptionManager> managers) {
        this.bus = bus;
        this.managers = managers.stream()
                                .collect(ImmutableMap.toImmutableMap(PlatformSubscriptionManager::getPlatform, Function.identity()));
    }

    @Override
    public @NonNull String getCallbackUrl(@NonNull Platform platform) {
        final var manager = managers.get(platform);
        if (manager == null) {
            throw new IllegalArgumentException("'"+platform+"' is not managed");
        }
        return manager.getCallbackUrl();
    }

    @Override
    public void requestSynchronization(@NonNull Platform platform) {
        bus.publishEvent(PlatformSync.forPlatform(platform));
    }

}
