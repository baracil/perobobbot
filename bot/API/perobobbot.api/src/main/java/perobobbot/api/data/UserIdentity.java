package perobobbot.api.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.ImmutableList;
import io.micronaut.serde.annotation.Serdeable;
import lombok.NonNull;
import perobobbot.api.Identification;

@Serdeable
public record UserIdentity(long id,
                           @NonNull Identification identification,
                           @NonNull UserType userType,
                           @NonNull String login, @NonNull String name,
                           @NonNull ImmutableList<JoinedChannel> joinedChannels) {
    @JsonIgnore
    public @NonNull Platform platform() {
        return identification.platform();
    }

    @JsonIgnore
    public @NonNull String userId() {
        return identification.userId();
    }


}
