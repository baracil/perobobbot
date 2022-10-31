package perobobbot.twitch.api.eventsub.event;

import lombok.NonNull;
import lombok.Value;
import perobobbot.twitch.api.UserInfo;

@Value
public class StreamOfflineEvent implements BroadcasterProvider, EventSubEvent {
    @NonNull UserInfo broadcaster;
}
