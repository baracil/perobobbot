package perobobbot.twitch.api.eventsub.subscription;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;
import perobobbot.twitch.api.CriteriaType;
import perobobbot.twitch.api.SubscriptionType;

@Value
@EqualsAndHashCode(callSuper = true)
public class ChannelSubscriptionEnd extends SingleConditionSubscription {

    public static final SubscriptionFactory FACTORY = forSingleCondition(CriteriaType.BROADCASTER_USER_ID, ChannelSubscriptionEnd::new);

    @NonNull String broadcasterId;

    public ChannelSubscriptionEnd(@NonNull String broadcasterId) {
        super(SubscriptionType.CHANNEL_SUBSCRIPTION_END,CriteriaType.BROADCASTER_USER_ID);
        this.broadcasterId = broadcasterId;
    }

    @Override
    protected String getConditionValue() {
        return broadcasterId;
    }
}
