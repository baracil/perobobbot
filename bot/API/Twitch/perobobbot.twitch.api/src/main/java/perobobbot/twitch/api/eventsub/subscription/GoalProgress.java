package perobobbot.twitch.api.eventsub.subscription;

import perobobbot.twitch.api.CriteriaType;
import perobobbot.twitch.api.SubscriptionType;

public class GoalProgress extends SingleConditionSubscription {

    public static final SubscriptionFactory FACTORY = forSingleCondition(CriteriaType.BROADCASTER_USER_ID, GoalProgress::new);

    private final String broadcasterId;

    public GoalProgress(String broadcasterId) {
        super(SubscriptionType.CHANNEL_GOAL_PROGRESS, CriteriaType.BROADCASTER_USER_ID);
        this.broadcasterId = broadcasterId;
    }

    @Override
    protected String getConditionValue() {
        return broadcasterId;
    }
}
