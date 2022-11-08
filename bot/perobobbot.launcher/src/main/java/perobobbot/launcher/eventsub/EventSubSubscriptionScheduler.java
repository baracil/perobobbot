package perobobbot.launcher.eventsub;

import io.micronaut.scheduling.annotation.Scheduled;
import jakarta.inject.Singleton;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.api.bus.Bus;
import perobobbot.api.bus.PlatformSync;

@Singleton
@RequiredArgsConstructor
public class EventSubSubscriptionScheduler {

    private final @NonNull Bus bus;

    @Scheduled(initialDelay = "10s",fixedDelay = "600s")
    public void requestEventSubSync() {
        bus.publishEvent(PlatformSync.forAllPlatforms());
    }
}
