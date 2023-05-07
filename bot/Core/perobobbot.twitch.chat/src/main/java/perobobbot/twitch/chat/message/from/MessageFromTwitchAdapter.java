package perobobbot.twitch.chat.message.from;

public abstract class MessageFromTwitchAdapter<T> implements MessageFromTwitchVisitor<T> {

    protected abstract T fallbackVisit(MessageFromTwitch messageFromTwitch);

    @Override
    public T visit(CapAck start) {
        return fallbackVisit(start);
    }

    @Override
    public T visit(HostTarget.Stop stop) {
        return fallbackVisit(stop);
    }

    @Override
    public T visit(HostTarget.Start start) {
        return fallbackVisit(start);
    }

    @Override
    public T visit(ClearChat clearChat) {
        return fallbackVisit(clearChat);
    }

    @Override
    public T visit(ClearMsg clearMsg) {
        return fallbackVisit(clearMsg);
    }

    @Override
    public T visit(GenericKnownMessageFromTwitch genericKnownMessageFromTwitch) {
        return fallbackVisit(genericKnownMessageFromTwitch);
    }

    @Override
    public T visit(GlobalUserState globalUserState) {
        return fallbackVisit(globalUserState);
    }

    @Override
    public T visit(InvalidIRCCommand invalidIRCCommand) {
        return fallbackVisit(invalidIRCCommand);
    }

    @Override
    public T visit(Join join) {
        return fallbackVisit(join);
    }

    @Override
    public T visit(Mode mode) {
        return fallbackVisit(mode);
    }

    @Override
    public T visit(Notice notice) {
        return fallbackVisit(notice);
    }

    @Override
    public T visit(Part part) {
        return fallbackVisit(part);
    }

    @Override
    public T visit(PingFromTwitch pingFromTwitch) {
        return fallbackVisit(pingFromTwitch);
    }

    @Override
    public T visit(PongFromTwitch pongFromTwitch) {
        return fallbackVisit(pongFromTwitch);
    }

    @Override
    public T visit(PrivMsgFromTwitch privMsgFromTwitch) {
        return fallbackVisit(privMsgFromTwitch);
    }

    @Override
    public T visit(RoomState roomState) {
        return fallbackVisit(roomState);
    }

    @Override
    public T visit(UnknownMessageFromTwitch unknownMessageFromTwitch) {
        return fallbackVisit(unknownMessageFromTwitch);
    }

    @Override
    public T visit(UserNotice userNotice) {
        return fallbackVisit(userNotice);
    }

    @Override
    public T visit(UserState userState) {
        return fallbackVisit(userState);
    }

    @Override
    public T visit(Welcome welcome) {
        return fallbackVisit(welcome);
    }
}
