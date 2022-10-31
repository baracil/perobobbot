package perobobbot.chat.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.micronaut.serde.annotation.Serdeable;
import lombok.NonNull;
import perobobbot.api.Identity;
import perobobbot.chat.api.irc.MessageData;

import java.util.Optional;

@Serdeable
public record ChatMessage(@NonNull Identity botId,
                          @NonNull MessageData data) {

    @JsonIgnore
    public @NonNull Optional<String> getPrivateMessage() {
        return data.getPrivateMessage();
    }
}
