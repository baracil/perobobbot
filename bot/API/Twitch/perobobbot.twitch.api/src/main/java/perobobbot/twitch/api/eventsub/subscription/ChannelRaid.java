package perobobbot.twitch.api.eventsub.subscription;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import perobobbot.twitch.api.Conditions;
import perobobbot.twitch.api.CriteriaType;
import perobobbot.twitch.api.SubscriptionType;

@Value
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



    public static @NonNull ChannelRaid from(@NonNull String broadcasterId) {
        return new ChannelRaid(broadcasterId,null);
    }

    public static @NonNull ChannelRaid to(@NonNull String broadcasterId) {
        return new ChannelRaid(null,broadcasterId);
    }

    String fromBroadcasterId;
    String toBroadcasterId;

    @Override
    public @NonNull SubscriptionType getType() {
        return SubscriptionType.CHANNEL_RAID;
    }

    @Override
    public @NonNull Conditions getCondition() {
        return Conditions.builder()
                         .put(CriteriaType.FROM_BROADCASTER_USER_ID,fromBroadcasterId)
                         .put(CriteriaType.TO_BROADCASTER_USER_ID,toBroadcasterId)
                         .build();
    }
}
