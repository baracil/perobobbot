package perobobbot.twitch.api.eventsub.event;

import lombok.NonNull;
import perobobbot.twitch.api.TwitchApiPayload;
import perobobbot.twitch.api.UserInfo;

import java.time.Instant;
import java.util.List;

public interface PredicationEvent extends BroadcasterProvider, EventSubEvent, TwitchApiPayload {
    @NonNull String getId();
    @NonNull UserInfo getBroadcaster();
    @NonNull String getTitle();
    @NonNull List<Outcome> getOutcomes();
    @NonNull Instant getStartedAt();
}
