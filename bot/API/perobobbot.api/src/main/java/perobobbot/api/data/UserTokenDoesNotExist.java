package perobobbot.api.data;

import lombok.Getter;

public class UserTokenDoesNotExist extends DataException {

    @Getter
    private final Platform platform;
    @Getter
    private final String userId;

    public UserTokenDoesNotExist(Platform platform, String userId) {
        super("No UserToken exists for platform '"+platform+"' for user '"+userId+"'");
        this.platform = platform;
        this.userId = userId;
    }
}
