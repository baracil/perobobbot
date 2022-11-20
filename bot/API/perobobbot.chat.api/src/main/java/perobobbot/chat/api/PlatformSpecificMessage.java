package perobobbot.chat.api;

import lombok.NonNull;
import lombok.Value;

import java.util.Optional;

@Value
public class PlatformSpecificMessage<T> {

    @NonNull T message;
    String privateMessage;

    public @NonNull Optional<String> getPrivateMessage() {
        return Optional.ofNullable(privateMessage);
    }
}
