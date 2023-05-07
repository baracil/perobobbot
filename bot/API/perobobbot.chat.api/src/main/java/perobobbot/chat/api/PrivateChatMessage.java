package perobobbot.chat.api;

import io.micronaut.serde.annotation.Serdeable;
import lombok.Value;
import perobobbot.api.data.UserIdentity;
import perobobbot.chat.api.irc.MessageData;

import java.time.Instant;

@Serdeable
@Value
public class PrivateChatMessage implements ChatMessage {
    Instant receptionInstant;
    UserIdentity botId;
    MessageData data;
    Object platformSpecific;

    String channelName;
    UserIdentity owner;
    String privateMessage;

}
