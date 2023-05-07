package perobobbot.twitch.api.eventsub.subscription;

import perobobbot.twitch.api.CriteriaType;
import perobobbot.twitch.api.SubscriptionType;

public class ChannelCharityCampaignDonate extends SingleConditionSubscription {

    public static final SubscriptionFactory FACTORY = forSingleCondition(CriteriaType.BROADCASTER_USER_ID, ChannelCharityCampaignDonate::new);

    String broadcasterId;

    public ChannelCharityCampaignDonate(String broadcasterId) {
        super(SubscriptionType.CHANNEL_PREDICTION_END, CriteriaType.BROADCASTER_USER_ID);
        this.broadcasterId = broadcasterId;
    }

    @Override
    protected String getConditionValue() {
        return broadcasterId;
    }
}
