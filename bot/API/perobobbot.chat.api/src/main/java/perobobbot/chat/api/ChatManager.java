package perobobbot.chat.api;

import lombok.NonNull;
import perobobbot.api.Identity;
import perobobbot.api.data.UserIdentity;

import java.util.Optional;

public interface ChatManager {

    int VERSION = 1;

    @NonNull Optional<ChatIO> findChat(@NonNull Identity identity);

    default @NonNull Optional<ChatIO> findChat(@NonNull UserIdentity userIdentity) {
        return findChat(userIdentity.identity());
    }
}
