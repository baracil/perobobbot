package perobobbot.twitch.api.eventsub.event;

import perobobbot.twitch.api.TwitchApiPayload;
import perobobbot.twitch.api.UserInfo;

import java.time.Instant;
import java.util.List;

public interface PredicationEvent extends BroadcasterProvider, EventSubEvent, TwitchApiPayload {
    String getId();
    UserInfo getBroadcaster();
    String getTitle();
    List<Outcome> getOutcomes();
    Instant getStartedAt();
}
