package perobobbot.chat.api;

import perobobbot.api.Identity;
import perobobbot.api.data.UserIdentity;

import java.util.Optional;

public interface ChatManager {

    int VERSION = 1;

    Optional<ChatIO> findChat(Identity identity);

    default Optional<ChatIO> findChat(UserIdentity userIdentity) {
        return findChat(userIdentity.identity());
    }
}
