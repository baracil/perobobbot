package perobobbot.twitch.api.eventsub;

import com.fasterxml.jackson.annotation.JsonAlias;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.annotation.Nullable;
import lombok.NonNull;
import lombok.Value;
import perobobbot.twitch.api.Pagination;

import java.util.Optional;

@Value
@Introspected
@Serdeable
public class TwitchSubscriptionData {

    int total;
    @NonNull TwitchSubscription[] data;
    @JsonAlias("total_cost")
    int totalCost;
    @JsonAlias("max_total_cost")
    int maxTotalCost;
    @Nullable
    Pagination pagination;//TODO handle pagination

    public @NonNull Optional<Pagination> getPagination() {
        return Optional.ofNullable(pagination);
    }
}
