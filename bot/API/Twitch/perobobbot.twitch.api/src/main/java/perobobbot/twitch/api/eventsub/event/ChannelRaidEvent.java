package perobobbot.twitch.api.eventsub.event;

import lombok.NonNull;
import lombok.Value;
import perobobbot.twitch.api.TwitchApiPayload;
import perobobbot.twitch.api.UserInfo;

@Value
public class ChannelRaidEvent implements BroadcasterProvider, EventSubEvent, TwitchApiPayload {

    @NonNull UserInfo fromBroadcaster;
    @NonNull UserInfo toBroadcaster;
    int viewers;

    @Override
    public @NonNull UserInfo getBroadcaster() {
        return toBroadcaster;
    }
}
