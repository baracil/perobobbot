package perobobbot.twitch.api.eventsub.event;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.Value;
import perobobbot.twitch.api.UserInfo;

import java.time.Instant;

@Value
public class PredictionLockEvent implements PredicationEvent {
    @NonNull String id;
    @NonNull UserInfo broadcaster;
    @NonNull String title;
    @NonNull ImmutableList<Outcome> outcomes;
    @NonNull Instant startedAt;

    @NonNull Instant lockedAt;

}
