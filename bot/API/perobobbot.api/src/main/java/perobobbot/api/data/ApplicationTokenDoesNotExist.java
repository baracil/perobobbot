package perobobbot.api.data;

import lombok.Getter;

public class ApplicationTokenDoesNotExist extends DataException {

    @Getter
    private final Platform platformName;

    public ApplicationTokenDoesNotExist(Platform platformName) {
        super("Platform '"+platformName+"' has not access token");
        this.platformName = platformName;
    }
}
