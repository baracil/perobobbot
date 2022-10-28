package perobobbot.twitch.web.api.eventsub;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.NonNull;
import lombok.Value;
import perobobbot.twitch.web.api.Pagination;

import java.util.Optional;

@Value
public class TwitchSubscriptionData {

    int total;
    @NonNull TwitchSubscription[] data;
    @JsonAlias("total_cost")
    int totalCost;
    @JsonAlias("max_total_cost")
    int maxTotalCost;
    Pagination pagination;//TODO handle pagination

    public @NonNull Optional<Pagination> getPagination() {
        return Optional.ofNullable(pagination);
    }
}
