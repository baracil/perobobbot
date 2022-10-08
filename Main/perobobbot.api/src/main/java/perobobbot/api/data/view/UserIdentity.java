package perobobbot.api.data.view;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.ImmutableList;
import io.micronaut.serde.annotation.Serdeable;
import lombok.NonNull;
import perobobbot.api.Identification;
import perobobbot.api.data.JoinedChannel;
import perobobbot.api.data.Platform;
import perobobbot.api.data.UserIdentityType;

@Serdeable
public record UserIdentity(long id,
                           @NonNull Identification identification,
                           @NonNull UserIdentityType userIdentityType,
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
