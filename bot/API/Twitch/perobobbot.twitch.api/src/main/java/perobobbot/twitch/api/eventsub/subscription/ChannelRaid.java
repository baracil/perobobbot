package perobobbot.twitch.api.eventsub.subscription;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import perobobbot.twitch.api.Conditions;
import perobobbot.twitch.api.CriteriaType;
import perobobbot.twitch.api.SubscriptionType;

@Value
@EqualsAndHashCode(callSuper = false)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ChannelRaid extends SubscriptionBase {

    public static final SubscriptionFactory FACTORY = condition -> {
        final var helper = new ConditionHelper(condition);
        final var from = helper.find(CriteriaType.FROM_BROADCASTER_USER_ID);
        final var to = helper.find(CriteriaType.TO_BROADCASTER_USER_ID);

        if (from.isPresent()) {
            return ChannelRaid.from(from.get());
        }
        if (to.isPresent()) {
            return ChannelRaid.to(to.get());
        }

        throw new IllegalArgumentException("Invalid condition.");
    };



    public static ChannelRaid from(String broadcasterId) {
        return new ChannelRaid(broadcasterId,null);
    }

    public static ChannelRaid to(String broadcasterId) {
        return new ChannelRaid(null,broadcasterId);
    }

    String fromBroadcasterId;
    String toBroadcasterId;

    @Override
    public SubscriptionType getType() {
        return SubscriptionType.CHANNEL_RAID;
    }

    @Override
    public Conditions getCondition() {
        return Conditions.builder()
                         .put(CriteriaType.FROM_BROADCASTER_USER_ID,fromBroadcasterId)
                         .put(CriteriaType.TO_BROADCASTER_USER_ID,toBroadcasterId)
                         .build();
    }
}
