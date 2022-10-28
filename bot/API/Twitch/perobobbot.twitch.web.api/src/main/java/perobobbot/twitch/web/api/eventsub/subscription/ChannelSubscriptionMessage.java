package perobobbot.twitch.web.api.eventsub.subscription;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;
import perobobbot.twitch.web.api.CriteriaType;
import perobobbot.twitch.web.api.SubscriptionType;

@Value
@EqualsAndHashCode(callSuper = true)
public class ChannelSubscriptionMessage extends SingleConditionSubscription {

    public static final SubscriptionFactory FACTORY = forSingleCondition(CriteriaType.BROADCASTER_USER_ID, ChannelSubscriptionMessage::new);

    @NonNull String broadcasterId;

    public ChannelSubscriptionMessage(@NonNull String broadcasterId) {
        super(SubscriptionType.CHANNEL_SUBSCRIPTION_MESSAGE,CriteriaType.BROADCASTER_USER_ID);
        this.broadcasterId = broadcasterId;
    }

    @Override
    protected String getConditionValue() {
        return broadcasterId;
    }
}
