package perobobbot.data.io;

import lombok.Getter;
import lombok.NonNull;

public class ApplicationForPlatformMissing extends DataException {

    @Getter
    private final @NonNull String platformName;

    public ApplicationForPlatformMissing(@NonNull String platformName) {
        super("No application for the platform '"+platformName+"' exists.");
        this.platformName = platformName;
    }
}
