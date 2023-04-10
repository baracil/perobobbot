package perobobbot.launcher.eventsub;

import io.micronaut.scheduling.annotation.Scheduled;
import jakarta.inject.Singleton;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.api.SubscriptionManager;
import perobobbot.api.event.PlatformSync;
import perobobbot.bus.api.Bus;
import perobobbot.bus.api.Producer;

import javax.annotation.PreDestroy;

@Singleton
@RequiredArgsConstructor
public class EventSubSubscriptionScheduler {


    private final Producer producer;

    public EventSubSubscriptionScheduler(@NonNull Bus bus) {
        this.producer = bus.createProducer(SubscriptionManager.SYSTEM_SUBSCRIPTION_TOPIC);
    }

    @PreDestroy
    public void onDestroy() {
        producer.close();
    }

    @Scheduled(initialDelay = "10s",fixedDelay = "600s")
    public void requestEventSubSync() {
        producer.send(PlatformSync.forAllPlatforms());
    }
}
