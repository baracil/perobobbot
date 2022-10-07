package perobobbot.twitch.chat.message.from;

import fpc.tools.irc.IRCParsing;
import fpc.tools.lang.CastTool;
import lombok.Getter;
import lombok.NonNull;
import perobobbot.twitch.chat.TwitchChannel;
import perobobbot.twitch.chat.message.IRCCommand;

/**
 * @author Bastien Aracil
 **/
@Getter
public abstract class HostTarget extends KnownMessageFromTwitch {


    private final int numberOfViewers;

    public HostTarget(@NonNull IRCParsing ircParsing, int numberOfViewers) {
        super(ircParsing);
        this.numberOfViewers = numberOfViewers;
    }

    @Override
    public @NonNull IRCCommand getCommand() {
        return IRCCommand.HOSTTARGET;
    }

    /**
     * @author Bastien Aracil
     **/
    @Getter
    public static class Start extends HostTarget {

        @NonNull
        private final TwitchChannel hostingChannel;

        public Start(
                @NonNull IRCParsing ircParsing,
                int numberOfViewers,
                @NonNull TwitchChannel hostingChannel) {
            super(ircParsing, numberOfViewers);
            this.hostingChannel = hostingChannel;
        }

        @Override
        public <T> T accept(@NonNull MessageFromTwitchVisitor<T> visitor) {
            return visitor.visit(this);
        }

    }

    /**
     * @author Bastien Aracil
     **/
    @Getter
    public static class Stop extends HostTarget {

        public Stop(@NonNull IRCParsing ircParsing, int numberOfViewers) {
            super(ircParsing, numberOfViewers);
        }

        @Override
        public <T> T accept(@NonNull MessageFromTwitchVisitor<T> visitor) {
            return visitor.visit(this);
        }
    }


    public static @NonNull HostTarget build(@NonNull AnswerBuilderHelper helper) {
        final String[] parameters = helper.lastParameter().split(" ");
        final int nbViewers = parameters.length<2? -1: CastTool.castToInt(parameters[1], -1);
        if (parameters[0].equals("-")) {
            return new Stop(helper.getIrcParsing(), nbViewers);
        }
        return new Start(helper.getIrcParsing(), nbViewers, TwitchChannel.create(parameters[0]));
    }
}
