package perobobbot.api.data;

import lombok.Getter;
import lombok.NonNull;

public class ApplicationTokenDoesNotExist extends DataException {

    @Getter
    private final @NonNull Platform platformName;

    public ApplicationTokenDoesNotExist(@NonNull Platform platformName) {
        super("Platform '"+platformName+"' has not access token");
        this.platformName = platformName;
    }
}
