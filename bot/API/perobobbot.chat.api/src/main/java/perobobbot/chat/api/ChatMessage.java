package perobobbot.chat.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.micronaut.serde.annotation.Serdeable;
import lombok.NonNull;
import lombok.Value;
import perobobbot.api.Identity;
import perobobbot.chat.api.irc.MessageData;

import java.util.Optional;

@Serdeable
@Value
public class ChatMessage implements ChatEvent {
    @NonNull Identity botId;
    @NonNull MessageData data;
    @NonNull Object platformSpecific;
    String privateMessage;


    public @NonNull Optional<String> getPrivateMessage() {
        return Optional.ofNullable(privateMessage);
    }

    @JsonIgnore
    public <T> @NonNull Optional<T> getPlatformSpecificAs(@NonNull Class<T> type) {
        if (type.isInstance(platformSpecific)) {
            return Optional.of(type.cast(platformSpecific));
        }
        return Optional.empty();
    }

}
