package perobobbot.twitch.eventsub;

import fpc.tools.lang.Instants;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
@RequiredArgsConstructor
public class SeenEventSubId {

    private final Instants instants;

    private final Map<String,Instant> seenIds = new ConcurrentHashMap<>();

    public boolean notAlreadySeen(String id) {
        return !seenIds.containsKey(id);
    }

    public void setSeen(String id) {
        seenIds.put(id,instants.now());
    }

    public void cleanSeenIds(Instant oldThreshold) {
        seenIds.values().removeIf(oldThreshold::isAfter);
    }


}
