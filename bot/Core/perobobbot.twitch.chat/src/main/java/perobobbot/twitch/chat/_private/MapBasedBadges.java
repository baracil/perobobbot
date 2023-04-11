package perobobbot.twitch.chat._private;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.twitch.chat.message.Badge;
import perobobbot.twitch.chat.message.Badges;

import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
public class MapBasedBadges implements Badges {

    @NonNull
    @Getter
    private final Map<String, Badge> badgesByName;

    @Override
    public boolean hasBadge(@NonNull String badgeName) {
        return badgesByName.containsKey(badgeName);
    }

    @Override
    public @NonNull Optional<Badge> findBadge(@NonNull String badgeName) {
        return Optional.ofNullable(badgesByName.get(badgeName));
    }
}
