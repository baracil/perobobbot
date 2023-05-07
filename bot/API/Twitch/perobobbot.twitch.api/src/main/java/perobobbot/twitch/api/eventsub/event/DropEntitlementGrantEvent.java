package perobobbot.twitch.api.eventsub.event;

import io.micronaut.serde.annotation.Serdeable;
import lombok.Value;
import perobobbot.twitch.api.TwitchApiPayload;

@Value
@Serdeable
public class DropEntitlementGrantEvent implements EventSubEvent, TwitchApiPayload {
    String id;
    Entitlement data;
}
