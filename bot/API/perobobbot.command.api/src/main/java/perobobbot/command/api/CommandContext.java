package perobobbot.command.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Value;
import perobobbot.api.data.Platform;
import perobobbot.api.data.UserIdentity;

import java.time.Instant;

@Value
@Serdeable
public class CommandContext {

    Instant triggerInstant;

    /**
     * The identity of the bot that connect to the channel
     */
    UserIdentity bot;
    /**
     * the owner of the message (the one that wrote it)
     */
    UserIdentity owner;
    /**
     * The name of the channel on which the message has
     * been written
     */
    String channelName;

    @JsonIgnore
    public Platform getPlatform() {
        return bot.platform();
    }
}
