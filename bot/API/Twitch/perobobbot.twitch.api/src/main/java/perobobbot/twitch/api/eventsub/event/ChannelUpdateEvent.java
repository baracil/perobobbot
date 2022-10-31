package perobobbot.twitch.api.eventsub.event;

import lombok.NonNull;
import lombok.Value;
import perobobbot.twitch.api.TwitchApiPayload;
import perobobbot.twitch.api.UserInfo;

@Value
public class ChannelUpdateEvent implements BroadcasterProvider, EventSubEvent, TwitchApiPayload {

    @NonNull UserInfo broadcaster;
    @NonNull String title;
    @NonNull String language;
    @NonNull String categoryId;
    @NonNull String category_name;
    boolean mature;
}
