package perobobbot.twitch.chat.message.from;

import fpc.tools.irc.IRCParsing;
import jakarta.annotation.Nullable;
import lombok.AccessLevel;
import lombok.Getter;
import perobobbot.twitch.chat.ChannelSpecific;
import perobobbot.twitch.chat.TwitchChannel;
import perobobbot.twitch.chat.message.IRCCommand;
import perobobbot.twitch.chat.message.TagKey;

import java.time.Duration;
import java.util.Optional;

/**
 * @author Bastien Aracil
 **/
@Getter
public class ClearChat extends KnownMessageFromTwitch implements ChannelSpecific {

    public static ClearChat permanentBan(IRCParsing ircParsing, String user, TwitchChannel channel) {
        return new ClearChat(ircParsing,user,channel,null);
    }

    public static ClearChat temporaryBan(IRCParsing ircParsing, String user, TwitchChannel channel, Duration banDuration) {
        return new ClearChat(ircParsing,user,channel,banDuration);
    }

    private final String user;

    private final TwitchChannel channel;

    @Getter(AccessLevel.NONE)
    private final @Nullable Duration banDuration;

    public ClearChat(IRCParsing ircParsing, String user, TwitchChannel channel, @Nullable Duration banDuration) {
        super(ircParsing);
        this.user = user;
        this.channel = channel;
        this.banDuration = banDuration;
    }

    @Override
    public IRCCommand getCommand() {
        return IRCCommand.CLEARCHAT;
    }

    public Optional<Duration> banDuration() {
        return Optional.ofNullable(banDuration);
    }

    public boolean isPermanentBan() {
        return banDuration == null;
    }

    @Override
    public <T> T accept(MessageFromTwitchVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public static ClearChat build(AnswerBuilderHelper helper) {
        final String user = helper.lastParameter();
        final TwitchChannel channel = helper.channelFromParameterAt(0);
        final Optional<Duration> banDuration = helper.optionalTagValueAsInt(TagKey.BAN_DURATION)
                                                     .map(Duration::ofSeconds);

        return banDuration.map(d -> ClearChat.temporaryBan(helper.getIrcParsing(), user, channel, d))
                          .orElseGet(() -> ClearChat.permanentBan(helper.getIrcParsing(), user, channel));
    }

}
