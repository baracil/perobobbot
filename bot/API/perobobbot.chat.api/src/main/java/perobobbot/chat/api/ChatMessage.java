package perobobbot.chat.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import perobobbot.api.data.UserIdentity;
import perobobbot.chat.api.irc.MessageData;

import java.util.Optional;

public interface ChatMessage extends ChatEvent {
    UserIdentity getBotId();
    MessageData getData();
    Object getPlatformSpecific();

    @JsonIgnore
    default <T> Optional<T> getPlatformSpecificAs(Class<T> type) {
        final var platformSpecific = getPlatformSpecific();
        if (type.isInstance(platformSpecific)) {
            return Optional.of(type.cast(platformSpecific));
        }
        return Optional.empty();
    }

}
