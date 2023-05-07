package perobobbot.twitch.chat.message.from;

import fpc.tools.irc.IRCParsing;
import perobobbot.twitch.chat.message.TagKey;
import perobobbot.twitch.chat.message.TagsAndBadges;

import java.util.Optional;

/**
 * @author Bastien Aracil
 **/
public interface MessageFromTwitch extends TagsAndBadges {

    IRCParsing getIrcParsing();

    default String getPayload() {
        return getIrcParsing().getRawMessage();
    }

    @Override
    default Optional<String> findTag(TagKey tagKey) {
        return getIrcParsing().tagValue(tagKey.getKeyName());
    }

    <T> T accept(MessageFromTwitchVisitor<T> visitor);
}
