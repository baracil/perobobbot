package perobobbot.data.io;

import lombok.Getter;
import lombok.NonNull;

public class ApplicationTokenDoesNotExist extends DataException {

    @Getter
    private final @NonNull String platformName;

    public ApplicationTokenDoesNotExist(@NonNull String platformName) {
        super("Platform '"+platformName+"' has not access token");
        this.platformName = platformName;
    }
}
