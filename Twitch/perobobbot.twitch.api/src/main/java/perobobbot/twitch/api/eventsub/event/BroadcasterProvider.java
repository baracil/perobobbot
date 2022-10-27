package perobobbot.twitch.api.eventsub.event;

import lombok.NonNull;
import perobobbot.twitch.api.UserInfo;

public interface BroadcasterProvider {

    @NonNull UserInfo getBroadcaster();

}
