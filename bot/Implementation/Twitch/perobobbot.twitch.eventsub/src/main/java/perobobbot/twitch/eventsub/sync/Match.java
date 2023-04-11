package perobobbot.twitch.eventsub.sync;

import lombok.*;
import perobobbot.api.SubscriptionData;
import perobobbot.api.SubscriptionView;
import perobobbot.twitch.api.eventsub.TwitchSubscription;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Getter
@Builder
public class Match {

    @Singular
    private final @NonNull List<String> toRevokeSubs;
    @Singular
    private final @NonNull Map<Long, TwitchSubscription> toUpdateSubs;
    @Singular
    private final @NonNull Map<Long, SubscriptionData> toReplaces;
    @Singular
    private final @NonNull List<SubscriptionView> toRefreshSubs;
    @Singular
    private final @NonNull List<SubscriptionView> toCreates;

    public boolean hasAnyChange() {
        return !toRevokeSubs.isEmpty() || !toUpdateSubs.isEmpty() || !toRefreshSubs.isEmpty() || !toCreates.isEmpty();
    }
}
