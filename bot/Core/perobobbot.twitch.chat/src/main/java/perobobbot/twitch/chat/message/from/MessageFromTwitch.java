package perobobbot.twitch.chat.message.from;

import fpc.tools.irc.IRCParsing;
import lombok.NonNull;
import perobobbot.twitch.chat.message.TagKey;
import perobobbot.twitch.chat.message.TagsAndBadges;

import java.util.Optional;

/**
 * @author Bastien Aracil
 **/
public interface MessageFromTwitch extends TagsAndBadges {

    @NonNull
    IRCParsing getIrcParsing();

    @NonNull
    default String getPayload() {
        return getIrcParsing().getRawMessage();
    }

    @Override
    default @NonNull Optional<String> findTag(@NonNull TagKey tagKey) {
        return getIrcParsing().tagValue(tagKey.getKeyName());
    }

    <T> T accept(@NonNull MessageFromTwitchVisitor<T> visitor);
}
