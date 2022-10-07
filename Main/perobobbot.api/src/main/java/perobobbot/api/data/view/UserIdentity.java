package perobobbot.api.data.view;

import lombok.NonNull;
import perobobbot.api.Identification;
import perobobbot.api.data.Platform;
import perobobbot.api.data.UserType;

public record UserIdentity(
        long id,
        @NonNull Identification identification,
        @NonNull UserType userType,
        @NonNull String login,
        @NonNull String name)  {

    public @NonNull Platform platform() {
        return identification.platform();
    }

    public @NonNull String userId() {
        return identification.userId();
    }


}
