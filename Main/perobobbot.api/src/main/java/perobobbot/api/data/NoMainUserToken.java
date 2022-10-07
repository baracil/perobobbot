package perobobbot.api.data;

import lombok.Getter;
import lombok.NonNull;

public class NoMainUserToken extends DataException {

    @Getter
    private final @NonNull Platform platform;

    public NoMainUserToken(@NonNull Platform platform) {
        super("No main user token is defined for platform '"+platform+"'");
        this.platform = platform;
    }
}
