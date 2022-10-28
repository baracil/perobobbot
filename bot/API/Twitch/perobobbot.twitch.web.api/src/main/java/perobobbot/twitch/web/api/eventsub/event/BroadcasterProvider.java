package perobobbot.twitch.web.api.eventsub.event;

import lombok.NonNull;
import perobobbot.twitch.web.api.UserInfo;

public interface BroadcasterProvider {

    @NonNull UserInfo getBroadcaster();

}
