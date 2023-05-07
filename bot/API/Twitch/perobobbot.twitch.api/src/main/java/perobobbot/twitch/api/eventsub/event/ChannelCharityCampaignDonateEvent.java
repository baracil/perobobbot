package perobobbot.twitch.api.eventsub.event;

import io.micronaut.serde.annotation.Serdeable;
import io.micronaut.serde.config.naming.SnakeCaseStrategy;
import lombok.Value;
import perobobbot.twitch.api.TwitchApiPayload;
import perobobbot.twitch.api.UserInfo;
import perobobbot.twitch.api.eventsub.Amount;

@Value
@Serdeable(naming = SnakeCaseStrategy.class)
public class ChannelCharityCampaignDonateEvent implements EventSubEvent, TwitchApiPayload, BroadcasterProvider {

    String campaignId;
    UserInfo broadcaster;
    UserInfo user;
    Amount amount;

}
