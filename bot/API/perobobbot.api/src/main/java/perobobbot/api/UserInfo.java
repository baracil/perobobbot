package perobobbot.api;

import lombok.NonNull;
import perobobbot.api.data.Platform;

public record UserInfo(
        @NonNull Identity identity,
        @NonNull String login,
        @NonNull String name) implements Identity.Provider {

    public @NonNull Platform platform() {
        return identity.platform();
    }

    public @NonNull String userId() {
        return identity.userId();
    }
}
