package perobobbot.chat.api;

import jakarta.annotation.Nullable;
import lombok.Value;

import java.util.Optional;

@Value
public class PlatformSpecificMessage<T> {

    T message;
    @Nullable
    String privateMessage;

    public Optional<String> getPrivateMessage() {
        return Optional.ofNullable(privateMessage);
    }
}
