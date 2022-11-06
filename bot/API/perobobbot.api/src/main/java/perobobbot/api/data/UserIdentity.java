package perobobbot.api.data;

import com.google.common.collect.ImmutableList;
import io.micronaut.core.annotation.Introspected;
import lombok.NonNull;
import perobobbot.api.Identity;

@Introspected
public record UserIdentity(long id,
                           @NonNull Identity identity,
                           @NonNull UserType userType,
                           @NonNull String login, @NonNull String name,
                           @NonNull ImmutableList<JoinedChannel> joinedChannels) {

    public @NonNull Platform platform() {
        return identity.platform();
    }

    public @NonNull String userId() {
        return identity.userId();
    }


}
