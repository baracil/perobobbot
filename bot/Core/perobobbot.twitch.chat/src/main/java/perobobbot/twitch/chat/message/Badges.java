package perobobbot.twitch.chat.message;

import lombok.NonNull;

import java.util.Optional;

public interface Badges {

    @NonNull
    Optional<Badge> findBadge(@NonNull String badgeName);

    default boolean hasBadge(@NonNull String badgeName) {
        return findBadge(badgeName).isPresent();
    }

    @NonNull
    Badges EMPTY = badgeName -> Optional.empty();

}
