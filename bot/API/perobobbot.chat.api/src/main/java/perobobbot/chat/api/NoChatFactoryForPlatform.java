package perobobbot.chat.api;

import lombok.Getter;
import lombok.NonNull;
import perobobbot.api.PerobobbotException;
import perobobbot.api.data.Platform;

@Getter
public class NoChatFactoryForPlatform extends PerobobbotException {

    private final @NonNull Platform platform;

    public NoChatFactoryForPlatform(@NonNull Platform platform) {
        super("Could not find any ChatFactory for platfor '"+platform+"'");
        this.platform = platform;
    }
}
