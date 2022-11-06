package perobobbot.twitch.eventsub.sync;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import lombok.*;
import perobobbot.api.SubscriptionData;
import perobobbot.api.SubscriptionView;
import perobobbot.twitch.api.eventsub.TwitchSubscription;

@RequiredArgsConstructor
@Getter
@Builder
public class Match {

    @Singular
    private final @NonNull ImmutableList<String> toRevokeSubs;
    @Singular
    private final @NonNull ImmutableMap<Long, TwitchSubscription> toUpdateSubs;
    @Singular
    private final @NonNull ImmutableMap<Long, SubscriptionData> toReplaces;
    @Singular
    private final @NonNull ImmutableList<SubscriptionView> toRefreshSubs;
    @Singular
    private final @NonNull ImmutableList<SubscriptionView> toCreates;

    public boolean hasAnyChange() {
        return !toRevokeSubs.isEmpty() || !toUpdateSubs.isEmpty() || !toRefreshSubs.isEmpty() || !toCreates.isEmpty();
    }
}
