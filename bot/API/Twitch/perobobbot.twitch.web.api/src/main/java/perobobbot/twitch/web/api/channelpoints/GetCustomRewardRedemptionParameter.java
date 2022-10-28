package perobobbot.twitch.web.api.channelpoints;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import perobobbot.twitch.web.api.Pagination;
import perobobbot.twitch.web.api.RewardRedemptionStatus;
import perobobbot.twitch.web.api.serde.RewardRedemptionStatusSerializer;

import java.util.Optional;

@Value
@Getter(AccessLevel.NONE)
@Builder(toBuilder = true)
public class GetCustomRewardRedemptionParameter {

    @Getter
    @NonNull String rewardId;

    String id;
    @JsonSerialize(using = RewardRedemptionStatusSerializer.class)
    RewardRedemptionStatus status;
    SortOrder sort;
    String after;
    Integer first;

    public @NonNull  Optional<String> getId() {
        return Optional.ofNullable(id);
    }

    public @NonNull  Optional<RewardRedemptionStatus> getStatus() {
        return Optional.ofNullable(status);
    }

    public @NonNull  Optional<SortOrder> getSort() {
        return Optional.ofNullable(sort);
    }

    public @NonNull  Optional<String> getAfter() {
        return Optional.ofNullable(after);
    }

    public @NonNull  Optional<Integer> getFirst() {
        return Optional.ofNullable(first);
    }


    public GetCustomRewardRedemptionParameter createNextPage(@NonNull Pagination pagination) {
        return toBuilder().after(pagination.getCursor()).build();
    }
}
