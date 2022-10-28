package perobobbot.twitch.web.api.eventsub.event;

import lombok.NonNull;
import lombok.Value;
import perobobbot.twitch.api.TwitchApiPayload;
import perobobbot.twitch.web.api.UserInfo;
import perobobbot.twitch.web.api.eventsub.Amount;

@Value
public class ChannelCharityCampaignDonateEvent implements EventSubEvent, TwitchApiPayload, BroadcasterProvider {

    @NonNull String campaignId;
    @NonNull UserInfo broadcaster;
    @NonNull UserInfo user;
    @NonNull Amount amount;

}
