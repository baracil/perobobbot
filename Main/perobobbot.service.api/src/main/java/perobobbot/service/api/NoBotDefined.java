package perobobbot.service.api;

import lombok.NonNull;
import perobobbot.api.PerobobbotException;
import perobobbot.api.data.Platform;

public class NoBotDefined extends PerobobbotException {

    private final @NonNull Platform platform;

    public NoBotDefined(@NonNull Platform platform) {
        super("No Bot defined for platform '"+platform.name()+"'");
        this.platform = platform;
    }


}
