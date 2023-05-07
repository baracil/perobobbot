package perobobbot.twitch.chat._private;

import fpc.tools.lang.MapTool;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import perobobbot.twitch.chat.message.Badge;

import java.util.Arrays;
import java.util.Map;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class BadgesParser {

    public static MapBasedBadges parse(String badgeListFromTag) {
        return new BadgesParser(badgeListFromTag.trim()).parse();
    }


    private final String badgeListFromTag;

    private MapBasedBadges parse() {
        if (badgeListFromTag.isEmpty()) {
            return new MapBasedBadges(Map.of());
        }

        final Map<String, Badge> badgesByName = Arrays.stream(badgeListFromTag.split(","))
                                                      .map(this::parseSingleBadge)
                                                      .collect(MapTool.collector(Badge::getName));
        return new MapBasedBadges(badgesByName);
    }

    private Badge parseSingleBadge(String singleBadgeFromTag) {
        final String[] tokens = singleBadgeFromTag.split("/");
        return Badge.with(tokens[0], tokens[1]);
    }

}
