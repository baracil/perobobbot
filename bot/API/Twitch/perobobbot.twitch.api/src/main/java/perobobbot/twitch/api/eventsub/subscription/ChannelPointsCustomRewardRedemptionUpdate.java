package perobobbot.twitch.api.eventsub.subscription;

import io.micronaut.core.annotation.Nullable;
import lombok.EqualsAndHashCode;
import lombok.Value;
import perobobbot.twitch.api.Conditions;
import perobobbot.twitch.api.CriteriaType;
import perobobbot.twitch.api.SubscriptionType;

@Value
@EqualsAndHashCode(callSuper = false)
public class ChannelPointsCustomRewardRedemptionUpdate extends SubscriptionBase {

    public static final SubscriptionFactory FACTORY = condition -> {
        final var helper = new ConditionHelper(condition);
        final var broadcasterId = helper.get(CriteriaType.BROADCASTER_USER_ID);
        final var rewardId = helper.find(CriteriaType.REWARD_ID);
        return rewardId.map(r -> new ChannelPointsCustomRewardRedemptionUpdate(broadcasterId, r))
                       .orElseGet(() -> new ChannelPointsCustomRewardRedemptionUpdate(broadcasterId));
    };



    String broadcasterId;
    @Nullable String rewardId;

    public ChannelPointsCustomRewardRedemptionUpdate(String broadcasterId) {
        this.broadcasterId = broadcasterId;
        this.rewardId = null;
    }

    public ChannelPointsCustomRewardRedemptionUpdate(String broadcasterId, String rewardId) {
        this.broadcasterId = broadcasterId;
        this.rewardId = rewardId;
    }

    @Override
    public SubscriptionType getType() {
        return SubscriptionType.CHANNEL_POINTS_CUSTOM_REWARD_REDEMPTION_UPDATE;
    }

    @Override
    public Conditions getCondition() {
        return Conditions.builder()
                .put(CriteriaType.BROADCASTER_USER_ID,broadcasterId)
                .put(CriteriaType.REWARD_ID,rewardId)
                .build();
    }
}
