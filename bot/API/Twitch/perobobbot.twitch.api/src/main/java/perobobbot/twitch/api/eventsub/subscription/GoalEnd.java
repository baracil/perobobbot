package perobobbot.twitch.api.eventsub.subscription;

import perobobbot.twitch.api.CriteriaType;
import perobobbot.twitch.api.SubscriptionType;

public class GoalEnd extends SingleConditionSubscription {

    public static final SubscriptionFactory FACTORY = forSingleCondition(CriteriaType.BROADCASTER_USER_ID, GoalEnd::new);

    private final String broadcasterId;

    public GoalEnd(String broadcasterId) {
        super(SubscriptionType.CHANNEL_GOAL_END, CriteriaType.BROADCASTER_USER_ID);
        this.broadcasterId = broadcasterId;
    }

    @Override
    protected String getConditionValue() {
        return broadcasterId;
    }
}
