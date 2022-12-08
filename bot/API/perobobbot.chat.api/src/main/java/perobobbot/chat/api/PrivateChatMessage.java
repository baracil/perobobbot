package perobobbot.chat.api;

import io.micronaut.serde.annotation.Serdeable;
import lombok.NonNull;
import lombok.Value;
import perobobbot.api.data.UserIdentity;
import perobobbot.chat.api.irc.MessageData;

import java.time.Instant;

@Serdeable
@Value
public class PrivateChatMessage implements ChatMessage {
    @NonNull Instant receptionInstant;
    @NonNull UserIdentity botId;
    @NonNull MessageData data;
    @NonNull Object platformSpecific;

    @NonNull String channelName;
    @NonNull UserIdentity owner;
    @NonNull String privateMessage;

}
