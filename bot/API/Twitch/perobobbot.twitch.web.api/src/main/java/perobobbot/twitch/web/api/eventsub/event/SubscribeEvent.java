package perobobbot.twitch.web.api.eventsub.event;

import lombok.NonNull;
import lombok.Value;
import perobobbot.twitch.web.api.UserInfo;
import perobobbot.twitch.web.api.eventsub.Tier;

@Value
public class SubscribeEvent implements BroadcasterProvider, EventSubEvent {
    @NonNull UserInfo user;
    @NonNull UserInfo broadcaster;
    @NonNull Tier tier;
    boolean gift;
}
