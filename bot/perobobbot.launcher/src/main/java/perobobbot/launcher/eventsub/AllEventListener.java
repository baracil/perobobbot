package perobobbot.launcher.eventsub;

import io.micronaut.scheduling.annotation.Async;
import jakarta.inject.Singleton;
import lombok.NonNull;
import perobobbot.api.bus.Event;

@Singleton
public class AllEventListener {

    @Async
    public void onEvent(@NonNull Event event) {
//        System.out.println(">>> event = "+event);
    }
}
