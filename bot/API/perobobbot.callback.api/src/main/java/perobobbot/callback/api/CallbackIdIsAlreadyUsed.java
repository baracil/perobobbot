package perobobbot.callback.api;

import lombok.Getter;
import perobobbot.api.PerobobbotException;

public class CallbackIdIsAlreadyUsed extends PerobobbotException {

    @Getter
    private final String id;

    public CallbackIdIsAlreadyUsed(String id) {
        this.id = id;
    }

}
