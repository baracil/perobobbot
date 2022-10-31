package perobobbot.api;

import lombok.NonNull;
import perobobbot.api.data.Platform;

public sealed interface Id permits Identity, BotId, IdentityByLogin {

    @NonNull Platform platform();

    <T> @NonNull T accept(@NonNull Visitor<T> visitor);

    interface Visitor<T> {
        @NonNull T visit(@NonNull Identity identity);
        @NonNull T visit(@NonNull BotId botId);
        @NonNull T visit(@NonNull IdentityByLogin identityByLogin);
    }
}
