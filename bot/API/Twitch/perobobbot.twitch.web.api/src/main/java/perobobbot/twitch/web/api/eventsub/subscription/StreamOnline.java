package perobobbot.twitch.web.api.eventsub.subscription;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;
import perobobbot.twitch.web.api.CriteriaType;
import perobobbot.twitch.web.api.SubscriptionType;

@Value
@EqualsAndHashCode(callSuper = true)
public class StreamOnline extends SingleConditionSubscription {

    public static final SubscriptionFactory FACTORY = forSingleCondition(CriteriaType.BROADCASTER_USER_ID, StreamOnline::new);

    @NonNull String broadcasterId;

    public StreamOnline(@NonNull String broadcasterId) {
        super(SubscriptionType.STREAM_ONLINE, CriteriaType.BROADCASTER_USER_ID);
        this.broadcasterId = broadcasterId;
    }

    @Override
    protected String getConditionValue() {
        return broadcasterId;
    }
}
