package perobobbot.twitch.chat.message.from;

import fpc.tools.irc.IRCParsing;
import lombok.Getter;
import perobobbot.twitch.chat._private.BadgesParser;
import perobobbot.twitch.chat.message.Badge;
import perobobbot.twitch.chat.message.Badges;

import java.util.Optional;

public abstract class MessageFromTwitchBase implements MessageFromTwitch {

    private final Badges badges;

    @Getter
    private final IRCParsing ircParsing;

    public MessageFromTwitchBase(IRCParsing ircParsing) {
        this.ircParsing = ircParsing;
        this.badges = ircParsing.tagValue("badges")
                .<Badges>map(BadgesParser::parse)
                .orElse(EMPTY);
    }

    @Override
    public Optional<String> findTag(String tagName) {
        return ircParsing.tagValue(tagName);
    }

    @Override
    public Optional<Badge> findBadge(String badgeName) {
        return badges.findBadge(badgeName);
    }

}
