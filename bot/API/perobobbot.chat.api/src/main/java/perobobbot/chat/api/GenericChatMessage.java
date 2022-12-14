package perobobbot.chat.api;

import io.micronaut.serde.annotation.Serdeable;
import lombok.NonNull;
import lombok.Value;
import perobobbot.api.data.UserIdentity;
import perobobbot.chat.api.irc.MessageData;

@Serdeable
@Value
public class GenericChatMessage implements ChatMessage {
    @NonNull UserIdentity botId;
    @NonNull MessageData data;
    @NonNull Object platformSpecific;
}
