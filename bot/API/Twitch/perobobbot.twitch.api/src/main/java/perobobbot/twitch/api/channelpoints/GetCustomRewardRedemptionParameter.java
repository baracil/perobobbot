package perobobbot.twitch.api.channelpoints;

import jakarta.annotation.Nullable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;
import perobobbot.twitch.api.Pagination;
import perobobbot.twitch.api.RewardRedemptionStatus;

import java.util.Optional;

@Value
@Getter(AccessLevel.NONE)
@Builder(toBuilder = true)
public class GetCustomRewardRedemptionParameter {

    @Getter
    String rewardId;

    @Nullable String id;
    @Nullable RewardRedemptionStatus status;
    @Nullable SortOrder sort;
    @Nullable String after;
    @Nullable Integer first;

    public  Optional<String> getId() {
        return Optional.ofNullable(id);
    }

    public  Optional<RewardRedemptionStatus> getStatus() {
        return Optional.ofNullable(status);
    }

    public  Optional<SortOrder> getSort() {
        return Optional.ofNullable(sort);
    }

    public  Optional<String> getAfter() {
        return Optional.ofNullable(after);
    }

    public  Optional<Integer> getFirst() {
        return Optional.ofNullable(first);
    }


    public GetCustomRewardRedemptionParameter createNextPage(Pagination pagination) {
        return toBuilder().after(pagination.getCursor()).build();
    }
}
