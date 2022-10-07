package perobobbot.twitch.chat.message.from;

import fpc.tools.irc.IRCParsing;
import lombok.Getter;
import lombok.NonNull;
import perobobbot.twitch.chat._private.BadgesParser;
import perobobbot.twitch.chat.message.Badge;
import perobobbot.twitch.chat.message.Badges;

import java.util.Optional;

public abstract class MessageFromTwitchBase implements MessageFromTwitch {

    @NonNull
    private final Badges badges;

    @NonNull
    @Getter
    private final IRCParsing ircParsing;

    public MessageFromTwitchBase(@NonNull IRCParsing ircParsing) {
        this.ircParsing = ircParsing;
        this.badges = ircParsing.tagValue("badges")
                .<Badges>map(BadgesParser::parse)
                .orElse(EMPTY);
    }

    @Override
    public @NonNull Optional<String> findTag(@NonNull String tagName) {
        return ircParsing.tagValue(tagName);
    }

    @Override
    public @NonNull Optional<Badge> findBadge(@NonNull String badgeName) {
        return badges.findBadge(badgeName);
    }

}
