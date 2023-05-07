package perobobbot.twitch.eventsub.sync;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Singular;
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
    private final List<String> toRevokeSubs;
    @Singular
    private final Map<Long, TwitchSubscription> toUpdateSubs;
    @Singular
    private final Map<Long, SubscriptionData> toReplaces;
    @Singular
    private final List<SubscriptionView> toRefreshSubs;
    @Singular
    private final List<SubscriptionView> toCreates;

    public boolean hasAnyChange() {
        return !toRevokeSubs.isEmpty() || !toUpdateSubs.isEmpty() || !toRefreshSubs.isEmpty() || !toCreates.isEmpty();
    }
}
