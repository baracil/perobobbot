package perobobbot.api;

import lombok.NonNull;
import perobobbot.api.data.Platform;

public record UserInfo(
        @NonNull Identification identification,
        @NonNull String login,
        @NonNull String name) implements Identification.Provider {

    public @NonNull Platform platform() {
        return identification.platform();
    }

    public @NonNull String userId() {
        return identification.userId();
    }
}
