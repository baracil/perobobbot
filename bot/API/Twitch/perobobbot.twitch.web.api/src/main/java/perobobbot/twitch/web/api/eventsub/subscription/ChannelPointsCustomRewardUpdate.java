package perobobbot.twitch.web.api.eventsub.subscription;

import lombok.NonNull;
import lombok.Value;
import perobobbot.twitch.web.api.Conditions;
import perobobbot.twitch.web.api.CriteriaType;
import perobobbot.twitch.web.api.SubscriptionType;

@Value
public class ChannelPointsCustomRewardUpdate extends SubscriptionBase {

    public static final SubscriptionFactory FACTORY = condition -> {
        final var helper = new ConditionHelper(condition);
        final var broadcasterId = helper.get(CriteriaType.BROADCASTER_USER_ID);
        final var rewardId = helper.find(CriteriaType.REWARD_ID);
        return rewardId.map(r -> new ChannelPointsCustomRewardUpdate(broadcasterId, r))
                       .orElseGet(() -> new ChannelPointsCustomRewardUpdate(broadcasterId));
    };


    @NonNull String broadcasterId;
    String rewardId;

    public ChannelPointsCustomRewardUpdate(@NonNull String broadcasterId) {
        this.broadcasterId = broadcasterId;
        this.rewardId = null;
    }

    public ChannelPointsCustomRewardUpdate(@NonNull String broadcasterId, @NonNull String rewardId) {
        this.broadcasterId = broadcasterId;
        this.rewardId = rewardId;
    }

    @Override
    public @NonNull SubscriptionType getType() {
        return SubscriptionType.CHANNEL_POINTS_CUSTOM_REWARD_REMOVE;
    }

    @Override
    public @NonNull Conditions getCondition() {
        return Conditions.builder()
                         .put(CriteriaType.BROADCASTER_USER_ID,broadcasterId)
                         .put(CriteriaType.REWARD_ID,rewardId)
                         .build();
    }
}
