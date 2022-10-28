package perobobbot.twitch.web.api.eventsub.event;

import lombok.NonNull;
import lombok.Value;
import perobobbot.twitch.web.api.UserInfo;

@Value
public class ExtensionBitsTransactionCreateEvent implements BroadcasterProvider, EventSubEvent {
    @NonNull String extensionClientId;
    @NonNull String id;
    @NonNull UserInfo broadcaster;
    @NonNull UserInfo user;
    @NonNull Product product;
}
