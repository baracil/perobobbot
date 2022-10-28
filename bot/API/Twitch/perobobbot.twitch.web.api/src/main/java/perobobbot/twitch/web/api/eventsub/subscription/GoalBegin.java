package perobobbot.twitch.web.api.eventsub.subscription;

import lombok.NonNull;
import perobobbot.twitch.web.api.CriteriaType;
import perobobbot.twitch.web.api.SubscriptionType;

public class GoalBegin extends SingleConditionSubscription {

    public static final SubscriptionFactory FACTORY = forSingleCondition(CriteriaType.BROADCASTER_USER_ID, GoalBegin::new);

    private final @NonNull String broadcasterId;

    public GoalBegin(@NonNull String broadcasterId) {
        super(SubscriptionType.CHANNEL_GOAL_BEGIN, CriteriaType.BROADCASTER_USER_ID);
        this.broadcasterId = broadcasterId;
    }

    @Override
    protected String getConditionValue() {
        return broadcasterId;
    }
}
