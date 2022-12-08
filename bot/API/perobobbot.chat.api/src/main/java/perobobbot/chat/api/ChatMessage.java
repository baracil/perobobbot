package perobobbot.chat.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.NonNull;
import perobobbot.api.data.UserIdentity;
import perobobbot.chat.api.irc.MessageData;

import java.util.Optional;

public interface ChatMessage extends ChatEvent {
    @NonNull UserIdentity getBotId();
    @NonNull MessageData getData();
    @NonNull Object getPlatformSpecific();

    @JsonIgnore
    default <T> @NonNull Optional<T> getPlatformSpecificAs(@NonNull Class<T> type) {
        final var platformSpecific = getPlatformSpecific();
        if (type.isInstance(platformSpecific)) {
            return Optional.of(type.cast(platformSpecific));
        }
        return Optional.empty();
    }

}
