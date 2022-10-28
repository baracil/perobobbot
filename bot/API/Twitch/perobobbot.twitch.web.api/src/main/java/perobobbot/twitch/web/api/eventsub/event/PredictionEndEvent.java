package perobobbot.twitch.web.api.eventsub.event;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.Value;
import perobobbot.twitch.web.api.UserInfo;

import java.time.Instant;

@Value
public class PredictionEndEvent implements PredicationEvent {
    @NonNull String id;
    @NonNull UserInfo broadcaster;
    @NonNull String title;
    @NonNull String winningOutcomeId;
    @NonNull ImmutableList<Outcome> outcomes;
    @NonNull PredicationStatus status;
    @NonNull Instant startedAt;

    @NonNull Instant endedAt;

}
