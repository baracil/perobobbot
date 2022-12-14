package perobobbot.twitch.chat.message;

import com.google.common.collect.ImmutableMap;
import fpc.tools.fp.Tuple2;
import fpc.tools.lang.MapTool;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.twitch.chat.message.from.*;

import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * @author Bastien Aracil
 **/
@RequiredArgsConstructor
public enum IRCCommand {
    CAP(CapAck::build),
    CLEARCHAT(ClearChat::build),
    CLEARMSG(ClearMsg::build),
    JOIN(Join::build),
    HOSTTARGET(HostTarget::build),
    MODE(Mode::build),
    NICK(),
    NOTICE(Notice::build),
    PART(Part::build),
    PASS(),
    PING(PingFromTwitch::build),
    PONG(PongFromTwitch::build),
    PRIVMSG(PrivMsgFromTwitch::build),
    RECONNECT(),
    ROOMSTATE(RoomState::build),
    RPL_WELCOME("001", Welcome::build),
    RPL_YOURHOST("002"),
    RPL_CREATED("003"),
    RPL_MYINFO("004"),
    RPL_NAMREPLY("353"),
    RPL_ENDOFNAMES("366"),
    RPL_MOTD("372"),
    RPL_MOTDSTART("375"),
    RPL_ENDOFMOTD("376"),
    USERNOTICE,
    USERSTATE(UserState::build),
    GLOBALUSERSTATE(GlobalUserState::build),
    ERR_UNKNOWNCOMMAND("421",InvalidIRCCommand::build),
    ;

    @NonNull
    private final String numericAlias;

    @NonNull
    private final Function<? super AnswerBuilderHelper, ? extends MessageFromTwitch> builder;

    IRCCommand() {
        this("");
    }

    IRCCommand(@NonNull String numericAlias) {
        this.numericAlias = numericAlias;
        this.builder = h -> new GenericKnownMessageFromTwitch(h.getIrcParsing(), this);
    }

    IRCCommand(@NonNull Function<? super AnswerBuilderHelper, ? extends MessageFromTwitch> builder) {
        this("",builder);
    }

    @NonNull
    public MessageFromTwitch buildTwitchAnswer(@NonNull AnswerBuilderHelper helper) {
        return builder.apply(helper);
    }




    @NonNull
    public static Optional<IRCCommand> findFromString(@NonNull String commandAsString) {
        return Optional.ofNullable(Holder.VALUES_BY_NAME.get(commandAsString.toLowerCase()));
    }

    private static class Holder {

        private static final ImmutableMap<String,IRCCommand> VALUES_BY_NAME;

        static {
            VALUES_BY_NAME = Stream.of(values())
                                   .flatMap(
                                           e -> Stream.of(
                                                   new Tuple2<>(e.name().toLowerCase(), e),
                                                   new Tuple2<>(e.numericAlias.toLowerCase(), e))
                                   )
                                   .filter(e -> !e.v1().isEmpty())
                                   .collect(MapTool.tuple2Collector());
        }
    }
}
