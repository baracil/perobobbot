package perobobbot.twitch.eventsub;

import java.time.Instant;

public record TwitchRequestContent<T>(String type,
                                      String messageId,
                                      Instant timeStamp,
                                      T content) {

    public <U> TwitchRequestContent<U> with(U newContent) {
        return new TwitchRequestContent<>(type,messageId,timeStamp,newContent);
    }

}
