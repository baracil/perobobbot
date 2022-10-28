package perobobbot.twitch.web.api.eventsub.event;

import lombok.NonNull;
import perobobbot.twitch.web.api.UserInfo;

public interface HypeTrainEvent extends BroadcasterProvider, EventSubEvent {

    @NonNull String getId();
    @NonNull UserInfo getBroadcaster();
    int getTotal();
}
