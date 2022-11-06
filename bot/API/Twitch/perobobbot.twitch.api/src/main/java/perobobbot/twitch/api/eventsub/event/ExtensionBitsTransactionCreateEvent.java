package perobobbot.twitch.api.eventsub.event;

import io.micronaut.serde.annotation.Serdeable;
import io.micronaut.serde.config.naming.SnakeCaseStrategy;
import lombok.NonNull;
import lombok.Value;
import perobobbot.twitch.api.TwitchApiPayload;
import perobobbot.twitch.api.UserInfo;

@Value
@Serdeable(naming = SnakeCaseStrategy.class)
public class ExtensionBitsTransactionCreateEvent implements BroadcasterProvider, EventSubEvent, TwitchApiPayload {
    @NonNull String extensionClientId;
    @NonNull String id;
    @NonNull UserInfo broadcaster;
    @NonNull UserInfo user;
    @NonNull Product product;
}
