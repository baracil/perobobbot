package perobobbot.api.data;

import lombok.Getter;
import lombok.NonNull;

public class DuplicateApplication extends DataException {

    @Getter
    private final @NonNull String applicationName;

    public DuplicateApplication(@NonNull String applicationName) {
        super("An application with the name '"+applicationName+"' exists already");
        this.applicationName = applicationName;
    }
}
