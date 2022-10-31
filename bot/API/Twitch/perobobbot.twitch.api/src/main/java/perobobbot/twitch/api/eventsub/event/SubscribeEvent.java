package perobobbot.twitch.api.eventsub.event;

import lombok.NonNull;
import lombok.Value;
import perobobbot.twitch.api.UserInfo;
import perobobbot.twitch.api.eventsub.Tier;

@Value
public class SubscribeEvent implements BroadcasterProvider, EventSubEvent {
    @NonNull UserInfo user;
    @NonNull UserInfo broadcaster;
    @NonNull Tier tier;
    boolean gift;
}
