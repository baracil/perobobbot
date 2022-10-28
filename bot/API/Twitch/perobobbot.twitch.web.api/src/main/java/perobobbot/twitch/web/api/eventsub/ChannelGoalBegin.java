package perobobbot.twitch.web.api.eventsub;

import lombok.NonNull;
import lombok.Value;
import perobobbot.twitch.web.api.CriteriaType;
import perobobbot.twitch.web.api.SubscriptionType;
import perobobbot.twitch.web.api.eventsub.subscription.SingleConditionSubscription;
import perobobbot.twitch.web.api.eventsub.subscription.SubscriptionFactory;

@Value
public class ChannelGoalBegin extends SingleConditionSubscription {

    public static final SubscriptionFactory FACTORY = forSingleCondition(CriteriaType.BROADCASTER_USER_ID,ChannelGoalBegin::new);

    @NonNull String broadcasterId;

    public ChannelGoalBegin(@NonNull String broadcasterId) {
        super(SubscriptionType.CHANNEL_GOAL_BEGIN, CriteriaType.BROADCASTER_USER_ID);
        this.broadcasterId = broadcasterId;
    }

    @Override
    protected String getConditionValue() {
        return broadcasterId;
    }
}
