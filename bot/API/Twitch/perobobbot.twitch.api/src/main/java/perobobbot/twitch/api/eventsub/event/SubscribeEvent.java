package perobobbot.twitch.api.eventsub.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.serde.annotation.Serdeable;
import lombok.NonNull;
import lombok.Value;
import perobobbot.twitch.api.TwitchApiPayload;
import perobobbot.twitch.api.UserInfo;
import perobobbot.twitch.api.eventsub.Tier;

@Value
@Serdeable
public class SubscribeEvent implements BroadcasterProvider, EventSubEvent, TwitchApiPayload {
    @NonNull UserInfo user;
    @NonNull UserInfo broadcaster;
    @NonNull Tier tier;
    @JsonProperty("is_gift")
    boolean gift;
}
