package perobobbot.chat.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.NonNull;
import perobobbot.api.Identity;
import perobobbot.chat.api.irc.MessageData;

import java.util.Optional;

public record ChatMessage(@NonNull Identity botId,
                          @NonNull MessageData data) implements ChatEvent {

    @JsonIgnore
    public @NonNull Optional<String> getPrivateMessage() {
        return data.getPrivateMessage();
    }
}
