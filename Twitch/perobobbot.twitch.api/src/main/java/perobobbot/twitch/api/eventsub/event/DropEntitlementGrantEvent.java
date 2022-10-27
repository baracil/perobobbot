package perobobbot.twitch.api.eventsub.event;

import com.google.common.collect.ImmutableList;
import lombok.Value;
import perobobbot.twitch.api.TwitchApiPayload;

@Value
public class DropEntitlementGrantEvent implements EventSubEvent, TwitchApiPayload {
    String id;
    ImmutableList<Entitlement> data;
}
