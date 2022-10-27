package perobobbot.twitch.api.eventsub;

import lombok.NonNull;

public interface EventSubHandlerChain<T> {

    void callNext(@NonNull T parameter);

}
