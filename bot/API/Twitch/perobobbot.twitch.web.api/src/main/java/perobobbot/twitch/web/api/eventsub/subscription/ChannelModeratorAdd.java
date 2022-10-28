package perobobbot.twitch.web.api.eventsub.subscription;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;
import perobobbot.twitch.web.api.CriteriaType;
import perobobbot.twitch.web.api.SubscriptionType;

@Value
@EqualsAndHashCode(callSuper = true)
public class ChannelModeratorAdd extends SingleConditionSubscription {

    public static final SubscriptionFactory FACTORY = forSingleCondition(CriteriaType.BROADCASTER_USER_ID, ChannelModeratorAdd::new);

    @NonNull String broadcasterId;

    public ChannelModeratorAdd(@NonNull String broadcasterId) {
        super(SubscriptionType.CHANNEL_MODERATOR_ADD,CriteriaType.BROADCASTER_USER_ID);
        this.broadcasterId = broadcasterId;
    }

    @Override
    protected String getConditionValue() {
        return broadcasterId;
    }
}
