package perobobbot.api.data.view;

import lombok.NonNull;
import perobobbot.api.data.Platform;

import java.util.Objects;

public record UserIdentity(@NonNull Platform platform,
                           @NonNull String userId,
                           @NonNull String login,
                           @NonNull String name) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserIdentity that = (UserIdentity) o;
        return platform.equals(that.platform) && userId.equals(that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(platform, userId);
    }
}
