package perobobbot.twitch.api.eventsub.subscription;

import lombok.Getter;
import lombok.NonNull;
import perobobbot.twitch.api.Conditions;
import perobobbot.twitch.api.CriteriaType;
import perobobbot.twitch.api.SubscriptionType;

@Getter
public class ChannelPointsCustomRewardRedemptionAdd extends SubscriptionBase {

    public static final SubscriptionFactory FACTORY = condition -> {

        final var helper = new ConditionHelper(condition);
        final var broadcasterId = helper.get(CriteriaType.BROADCASTER_USER_ID);
        final var rewardId = helper.find(CriteriaType.REWARD_ID);
        return rewardId.map(r -> new ChannelPointsCustomRewardRedemptionAdd(broadcasterId, r))
                       .orElseGet(() -> new ChannelPointsCustomRewardRedemptionAdd(broadcasterId));
    };

    @NonNull String broadcasterId;
    String rewardId;

    public ChannelPointsCustomRewardRedemptionAdd(@NonNull String broadcasterId) {
        this.broadcasterId = broadcasterId;
        this.rewardId = null;
    }

    public ChannelPointsCustomRewardRedemptionAdd(@NonNull String broadcasterId, @NonNull String rewardId) {
        this.broadcasterId = broadcasterId;
        this.rewardId = rewardId;
    }

    @Override
    public @NonNull SubscriptionType getType() {
        return SubscriptionType.CHANNEL_POINTS_CUSTOM_REWARD_REDEMPTION_ADD;
    }

    @Override
    public @NonNull Conditions getCondition() {
        return Conditions.builder()
                .put(CriteriaType.BROADCASTER_USER_ID,broadcasterId)
                .put(CriteriaType.REWARD_ID,rewardId)
                .build();
    }
}
