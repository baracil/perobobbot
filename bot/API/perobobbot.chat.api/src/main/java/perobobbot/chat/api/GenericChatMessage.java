package perobobbot.chat.api;

import io.micronaut.serde.annotation.Serdeable;
import lombok.Value;
import perobobbot.api.data.UserIdentity;
import perobobbot.chat.api.irc.MessageData;

@Serdeable
@Value
public class GenericChatMessage implements ChatMessage {
    UserIdentity botId;
    MessageData data;
    Object platformSpecific;
}
