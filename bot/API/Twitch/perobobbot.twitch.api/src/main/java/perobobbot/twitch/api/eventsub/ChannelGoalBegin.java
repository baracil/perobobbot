package perobobbot.twitch.api.eventsub;

import lombok.Value;
import perobobbot.twitch.api.CriteriaType;
import perobobbot.twitch.api.SubscriptionType;
import perobobbot.twitch.api.eventsub.subscription.SingleConditionSubscription;
import perobobbot.twitch.api.eventsub.subscription.SubscriptionFactory;

@Value
public class ChannelGoalBegin extends SingleConditionSubscription {

    public static final SubscriptionFactory FACTORY = forSingleCondition(CriteriaType.BROADCASTER_USER_ID,ChannelGoalBegin::new);

    String broadcasterId;

    public ChannelGoalBegin(String broadcasterId) {
        super(SubscriptionType.CHANNEL_GOAL_BEGIN, CriteriaType.BROADCASTER_USER_ID);
        this.broadcasterId = broadcasterId;
    }

    @Override
    protected String getConditionValue() {
        return broadcasterId;
    }
}
