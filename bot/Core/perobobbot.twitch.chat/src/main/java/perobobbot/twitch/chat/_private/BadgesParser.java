package perobobbot.twitch.chat._private;

import com.google.common.collect.ImmutableMap;
import fpc.tools.lang.MapTool;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.twitch.chat.message.Badge;

import java.util.Arrays;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class BadgesParser {

    @NonNull
    public static MapBasedBadges parse(@NonNull String badgeListFromTag) {
        return new BadgesParser(badgeListFromTag.trim()).parse();
    }


    @NonNull
    private final String badgeListFromTag;

    @NonNull
    private MapBasedBadges parse() {
        if (badgeListFromTag.isEmpty()) {
            return new MapBasedBadges(ImmutableMap.of());
        }

        final ImmutableMap<String, Badge> badgesByName = Arrays.stream(badgeListFromTag.split(","))
                                                               .map(this::parseSingleBadge)
                                                               .collect(MapTool.collector(Badge::getName));
        return new MapBasedBadges(badgesByName);
    }

    @NonNull
    private Badge parseSingleBadge(String singleBadgeFromTag) {
        final String[] tokens = singleBadgeFromTag.split("/");
        return Badge.with(tokens[0], tokens[1]);
    }

}
