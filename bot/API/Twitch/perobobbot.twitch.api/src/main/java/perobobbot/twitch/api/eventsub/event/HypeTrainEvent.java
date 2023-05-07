package perobobbot.twitch.api.eventsub.event;

import perobobbot.twitch.api.TwitchApiPayload;
import perobobbot.twitch.api.UserInfo;


public interface HypeTrainEvent extends BroadcasterProvider, EventSubEvent, TwitchApiPayload {

    String getId();
    UserInfo getBroadcaster();
    int getTotal();
}
