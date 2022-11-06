package perobobbot.twitch.api.eventsub.event;

import lombok.NonNull;
import perobobbot.twitch.api.TwitchApiPayload;
import perobobbot.twitch.api.UserInfo;


public interface HypeTrainEvent extends BroadcasterProvider, EventSubEvent, TwitchApiPayload {

    @NonNull String getId();
    @NonNull UserInfo getBroadcaster();
    int getTotal();
}
