package perobobbot.twitch.web.api.eventsub.event;

import lombok.NonNull;
import lombok.Value;
import perobobbot.twitch.web.api.UserInfo;

import java.time.Instant;

@Value
public class StreamOnlineEvent implements BroadcasterProvider, EventSubEvent {
    @NonNull String id;
    @NonNull UserInfo broadcaster;
    @NonNull StreamType type;
    @NonNull Instant startedAt;

}
