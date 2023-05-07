package perobobbot.api.data;

import lombok.Getter;

public class DuplicateApplication extends DataException {

    @Getter
    private final String applicationName;

    public DuplicateApplication(String applicationName) {
        super("An application with the name '"+applicationName+"' exists already");
        this.applicationName = applicationName;
    }
}
