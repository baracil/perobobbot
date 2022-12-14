package perobobbot.twitch.chat.message.from;

import fpc.tools.irc.IRCParsing;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import perobobbot.twitch.chat.ChannelSpecific;
import perobobbot.twitch.chat.TwitchChannel;
import perobobbot.twitch.chat.message.IRCCommand;

/**
 * @author Bastien Aracil
 **/
@Getter
public class Part extends KnownMessageFromTwitch implements ChannelSpecific {

    @NonNull
    private final String user;

    @NonNull
    private final TwitchChannel channel;

    @Builder
    public Part(@NonNull IRCParsing ircParsing, @NonNull String user, @NonNull TwitchChannel channel) {
        super(ircParsing);
        this.user = user;
        this.channel = channel;
    }

    @Override
    public @NonNull IRCCommand getCommand() {
        return IRCCommand.PART;
    }

    @Override
    public <T> T accept(@NonNull MessageFromTwitchVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public static @NonNull Part build(@NonNull AnswerBuilderHelper helper) {
        return Part.builder()
                   .ircParsing(helper.getIrcParsing())
                   .channel(helper.channelFromParameterAt(0))
                   .user(helper.userFromPrefix())
                   .build();
    }
}
