package perobobbot.twitch.chat.message.from;

import fpc.tools.irc.IRCParsing;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
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

    @NonNull
    public static ClearChat permanentBan(@NonNull IRCParsing ircParsing, @NonNull String user, @NonNull TwitchChannel channel) {
        return new ClearChat(ircParsing,user,channel,null);
    }

    @NonNull
    public static ClearChat temporaryBan(@NonNull IRCParsing ircParsing, @NonNull String user, @NonNull TwitchChannel channel, @NonNull Duration banDuration) {
        return new ClearChat(ircParsing,user,channel,banDuration);
    }

    @NonNull
    private final String user;

    @NonNull
    private final TwitchChannel channel;

    @Getter(AccessLevel.NONE)
    private final Duration banDuration;

    public ClearChat(@NonNull IRCParsing ircParsing, @NonNull String user, @NonNull TwitchChannel channel, Duration banDuration) {
        super(ircParsing);
        this.user = user;
        this.channel = channel;
        this.banDuration = banDuration;
    }

    @Override
    public @NonNull IRCCommand getCommand() {
        return IRCCommand.CLEARCHAT;
    }

    @NonNull
    public Optional<Duration> banDuration() {
        return Optional.ofNullable(banDuration);
    }

    public boolean isPermanentBan() {
        return banDuration == null;
    }

    @Override
    public <T> T accept(@NonNull MessageFromTwitchVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public static @NonNull ClearChat build(@NonNull AnswerBuilderHelper helper) {
        final String user = helper.lastParameter();
        final TwitchChannel channel = helper.channelFromParameterAt(0);
        final Optional<Duration> banDuration = helper.optionalTagValueAsInt(TagKey.BAN_DURATION)
                                                     .map(Duration::ofSeconds);

        return banDuration.map(d -> ClearChat.temporaryBan(helper.getIrcParsing(), user, channel, d))
                          .orElseGet(() -> ClearChat.permanentBan(helper.getIrcParsing(), user, channel));
    }

}
