package perobobbot.twitch.api.eventsub.subscription;

import perobobbot.twitch.api.CriteriaType;
import perobobbot.twitch.api.SubscriptionType;

public class GoalBegin extends SingleConditionSubscription {

    public static final SubscriptionFactory FACTORY = forSingleCondition(CriteriaType.BROADCASTER_USER_ID, GoalBegin::new);

    private final String broadcasterId;

    public GoalBegin(String broadcasterId) {
        super(SubscriptionType.CHANNEL_GOAL_BEGIN, CriteriaType.BROADCASTER_USER_ID);
        this.broadcasterId = broadcasterId;
    }

    @Override
    protected String getConditionValue() {
        return broadcasterId;
    }
}
