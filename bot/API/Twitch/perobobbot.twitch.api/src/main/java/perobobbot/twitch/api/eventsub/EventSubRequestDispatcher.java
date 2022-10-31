package perobobbot.twitch.api.eventsub;

import fpc.tools.lang.Subscription;
import lombok.NonNull;

public interface EventSubRequestDispatcher {

    int VERSION = 1;

    void dispatchEventSub(@NonNull EventSubRequest eventSubRequest);

    @NonNull Subscription addListener(@NonNull EventSubListener listener);

}
