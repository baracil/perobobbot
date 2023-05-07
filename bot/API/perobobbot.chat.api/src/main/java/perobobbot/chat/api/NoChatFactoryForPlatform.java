package perobobbot.chat.api;

import lombok.Getter;
import perobobbot.api.PerobobbotException;
import perobobbot.api.data.Platform;

@Getter
public class NoChatFactoryForPlatform extends PerobobbotException {

    private final Platform platform;

    public NoChatFactoryForPlatform(Platform platform) {
        super("Could not find any ChatFactory for platfor '"+platform+"'");
        this.platform = platform;
    }
}
