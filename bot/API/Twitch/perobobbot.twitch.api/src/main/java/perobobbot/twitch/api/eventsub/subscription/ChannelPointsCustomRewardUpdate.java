package perobobbot.twitch.api.eventsub.subscription;

import jakarta.annotation.Nullable;
import lombok.Value;
import perobobbot.twitch.api.Conditions;
import perobobbot.twitch.api.CriteriaType;
import perobobbot.twitch.api.SubscriptionType;

@Value
public class ChannelPointsCustomRewardUpdate extends SubscriptionBase {

    public static final SubscriptionFactory FACTORY = condition -> {
        final var helper = new ConditionHelper(condition);
        final var broadcasterId = helper.get(CriteriaType.BROADCASTER_USER_ID);
        final var rewardId = helper.find(CriteriaType.REWARD_ID);
        return rewardId.map(r -> new ChannelPointsCustomRewardUpdate(broadcasterId, r))
                       .orElseGet(() -> new ChannelPointsCustomRewardUpdate(broadcasterId));
    };


    String broadcasterId;
    @Nullable String rewardId;

    public ChannelPointsCustomRewardUpdate(String broadcasterId) {
        this.broadcasterId = broadcasterId;
        this.rewardId = null;
    }

    public ChannelPointsCustomRewardUpdate(String broadcasterId, String rewardId) {
        this.broadcasterId = broadcasterId;
        this.rewardId = rewardId;
    }

    @Override
    public SubscriptionType getType() {
        return SubscriptionType.CHANNEL_POINTS_CUSTOM_REWARD_REMOVE;
    }

    @Override
    public Conditions getCondition() {
        return Conditions.builder()
                         .put(CriteriaType.BROADCASTER_USER_ID,broadcasterId)
                         .put(CriteriaType.REWARD_ID,rewardId)
                         .build();
    }
}
