package perobobbot.twitch.api.eventsub.event;

import lombok.NonNull;
import lombok.Value;
import perobobbot.twitch.api.TwitchApiPayload;
import perobobbot.twitch.api.UserInfo;
import perobobbot.twitch.api.eventsub.Amount;

@Value
public class ChannelCharityCampaignDonateEvent implements EventSubEvent, TwitchApiPayload, BroadcasterProvider {

    @NonNull String campaignId;
    @NonNull UserInfo broadcaster;
    @NonNull UserInfo user;
    @NonNull Amount amount;

}
