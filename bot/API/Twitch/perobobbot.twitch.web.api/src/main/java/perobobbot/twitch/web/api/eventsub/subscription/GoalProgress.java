package perobobbot.twitch.web.api.eventsub.subscription;

import lombok.NonNull;
import perobobbot.twitch.web.api.CriteriaType;
import perobobbot.twitch.web.api.SubscriptionType;

public class GoalProgress extends SingleConditionSubscription {

    public static final SubscriptionFactory FACTORY = forSingleCondition(CriteriaType.BROADCASTER_USER_ID, GoalProgress::new);

    private final @NonNull String broadcasterId;

    public GoalProgress(@NonNull String broadcasterId) {
        super(SubscriptionType.CHANNEL_GOAL_PROGRESS, CriteriaType.BROADCASTER_USER_ID);
        this.broadcasterId = broadcasterId;
    }

    @Override
    protected String getConditionValue() {
        return broadcasterId;
    }
}
