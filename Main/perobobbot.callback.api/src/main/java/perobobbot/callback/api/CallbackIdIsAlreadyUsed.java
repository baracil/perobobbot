package perobobbot.callback.api;

import lombok.Getter;
import lombok.NonNull;
import perobobbot.api.PerobobbotException;

public class CallbackIdIsAlreadyUsed extends PerobobbotException {

    @Getter
    private final @NonNull String id;

    public CallbackIdIsAlreadyUsed(@NonNull String id) {
        this.id = id;
    }

}
