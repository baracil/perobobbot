package perobobbot.twitch.api.eventsub.subscription;

import lombok.NonNull;
import perobobbot.twitch.api.CriteriaType;
import perobobbot.twitch.api.SubscriptionType;

public class GoalEnd extends SingleConditionSubscription {

    public static final SubscriptionFactory FACTORY = forSingleCondition(CriteriaType.BROADCASTER_USER_ID, GoalEnd::new);

    private final @NonNull String broadcasterId;

    public GoalEnd(@NonNull String broadcasterId) {
        super(SubscriptionType.CHANNEL_GOAL_END, CriteriaType.BROADCASTER_USER_ID);
        this.broadcasterId = broadcasterId;
    }

    @Override
    protected String getConditionValue() {
        return broadcasterId;
    }
}
