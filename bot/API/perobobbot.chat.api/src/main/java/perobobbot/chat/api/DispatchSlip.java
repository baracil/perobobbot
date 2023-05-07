package perobobbot.chat.api;

import java.time.Instant;

public interface DispatchSlip {

    /**
     * @return the time when the command was sent
     */
    Instant getDispatchingTime();

}
