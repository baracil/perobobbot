package perobobbot.twitch.web.api.eventsub.subscription;

import lombok.NonNull;
import perobobbot.twitch.web.api.CriteriaType;
import perobobbot.twitch.web.api.SubscriptionType;

public class ChannelCharityCampaignDonate extends SingleConditionSubscription {

    public static final SubscriptionFactory FACTORY = forSingleCondition(CriteriaType.BROADCASTER_USER_ID, ChannelCharityCampaignDonate::new);

    @NonNull String broadcasterId;

    public ChannelCharityCampaignDonate(@NonNull String broadcasterId) {
        super(SubscriptionType.CHANNEL_PREDICTION_END, CriteriaType.BROADCASTER_USER_ID);
        this.broadcasterId = broadcasterId;
    }

    @Override
    protected String getConditionValue() {
        return broadcasterId;
    }
}
