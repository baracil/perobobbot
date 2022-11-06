package perobobbot.twitch.api.eventsub.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.serde.annotation.Serdeable;
import io.micronaut.serde.config.naming.SnakeCaseStrategy;
import lombok.NonNull;
import lombok.Value;
import perobobbot.twitch.api.TwitchApiPayload;
import perobobbot.twitch.api.UserInfo;

@Value
@Serdeable(naming = SnakeCaseStrategy.class)
public class ChannelUpdateEvent implements BroadcasterProvider, EventSubEvent, TwitchApiPayload {

    @NonNull UserInfo broadcaster;
    @NonNull String title;
    @NonNull String language;
    @NonNull String categoryId;
    @NonNull String categoryName;
    @JsonProperty("is_mature")
    boolean mature;
}
