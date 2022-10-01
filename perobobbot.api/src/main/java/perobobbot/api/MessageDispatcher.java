package perobobbot.api;

import lombok.NonNull;

public interface MessageDispatcher {

    void dispatch(@NonNull String message);

}
