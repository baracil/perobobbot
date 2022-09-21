package perobobbot.api.data;

import lombok.Getter;
import lombok.NonNull;

public class UserTokenDoesNotExist extends DataException {

    @Getter
    private final @NonNull Platform platform;

    public UserTokenDoesNotExist(@NonNull Platform platform) {
        super("No UserToken exists for platform '"+platform+"'");
        this.platform = platform;
    }
}
