package perobobbot.twitch.api.eventsub;

import io.micronaut.core.annotation.Nullable;
import io.micronaut.serde.annotation.Serdeable;
import io.micronaut.serde.config.naming.SnakeCaseStrategy;
import lombok.NonNull;
import lombok.Value;
import perobobbot.twitch.api.Pagination;

import java.util.Optional;

@Value
@Serdeable(naming = SnakeCaseStrategy.class)
public class TwitchSubscriptionData {

    int total;
    @NonNull TwitchSubscription[] data;
    int totalCost;
    int maxTotalCost;
    @Nullable
    Pagination pagination;//TODO handle pagination

    public @NonNull Optional<Pagination> getPagination() {
        return Optional.ofNullable(pagination);
    }
}
