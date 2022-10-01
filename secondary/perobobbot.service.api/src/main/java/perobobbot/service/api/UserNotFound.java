package perobobbot.service.api;

import lombok.Getter;
import lombok.NonNull;
import perobobbot.api.data.Platform;

@Getter
public class UserNotFound extends DomainException {

    private final @NonNull Platform platform;
    private final @NonNull String userId;

    public UserNotFound(@NonNull Platform platform, @NonNull String userId) {
        super("Could not find any user on platform '"+platform+"' with user Id '"+userId+"'");
        this.platform = platform;
        this.userId = userId;
    }
}
