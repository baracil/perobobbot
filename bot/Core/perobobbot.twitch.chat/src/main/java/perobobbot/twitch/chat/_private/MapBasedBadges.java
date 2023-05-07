package perobobbot.twitch.chat._private;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import perobobbot.twitch.chat.message.Badge;
import perobobbot.twitch.chat.message.Badges;

import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
public class MapBasedBadges implements Badges {

    @Getter
    private final Map<String, Badge> badgesByName;

    @Override
    public boolean hasBadge(String badgeName) {
        return badgesByName.containsKey(badgeName);
    }

    @Override
    public Optional<Badge> findBadge(String badgeName) {
        return Optional.ofNullable(badgesByName.get(badgeName));
    }
}
