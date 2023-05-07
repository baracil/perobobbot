package perobobbot.twitch.chat.message;

import java.util.Optional;

public interface Badges {

    Optional<Badge> findBadge(String badgeName);

    default boolean hasBadge(String badgeName) {
        return findBadge(badgeName).isPresent();
    }

    Badges EMPTY = badgeName -> Optional.empty();

}
