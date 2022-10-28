package perobobbot.api.data;

import lombok.Getter;
import lombok.NonNull;

public class UserTokenDoesNotExist extends DataException {

    @Getter
    private final @NonNull Platform platform;
    @Getter
    private final @NonNull String userId;

    public UserTokenDoesNotExist(@NonNull Platform platform, @NonNull String userId) {
        super("No UserToken exists for platform '"+platform+"' for user '"+userId+"'");
        this.platform = platform;
        this.userId = userId;
    }
}
