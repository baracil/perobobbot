package perobobbot.chat.api;

import lombok.NonNull;

import java.time.Instant;

public interface DispatchSlip {

    /**
     * @return the time when the command was sent
     */
    @NonNull
    Instant getDispatchingTime();

}
