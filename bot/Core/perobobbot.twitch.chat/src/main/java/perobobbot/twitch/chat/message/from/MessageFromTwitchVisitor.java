package perobobbot.twitch.chat.message.from;

import fpc.tools.fp.Function1;

public interface MessageFromTwitchVisitor<T> {

    default Function1<MessageFromTwitch, T> toFunction() {
        return m -> m.accept(this);
    }

    T visit(CapAck start);

    T visit(HostTarget.Stop stop);

    T visit(HostTarget.Start start);

    T visit(ClearChat clearChat);

    T visit(ClearMsg clearMsg);

    T visit(GenericKnownMessageFromTwitch genericKnownMessageFromTwitch);

    T visit(GlobalUserState globalUserState);

    T visit(InvalidIRCCommand invalidIRCCommand);

    T visit(Join join);

    T visit(Mode mode);

    T visit(Notice notice);

    T visit(Part part);

    T visit(PingFromTwitch pingFromTwitch);

    T visit(PongFromTwitch pongFromTwitch);

    T visit(PrivMsgFromTwitch privMsgFromTwitch);

    T visit(RoomState roomState);

    T visit(UnknownMessageFromTwitch unknownMessageFromTwitch);

    T visit(UserNotice userNotice);

    T visit(UserState userState);

    T visit(Welcome welcome);
}
