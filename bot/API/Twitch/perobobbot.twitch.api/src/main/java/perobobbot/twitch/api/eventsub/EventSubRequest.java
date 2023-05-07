package perobobbot.twitch.api.eventsub;

public interface EventSubRequest {

    <T> T accept(Visitor<T> visitor);

    interface Visitor<T> {

        T visit(EventSubNotification notification);
        T visit(EventSubRevocation revocation);
        T visit(EventSubVerification verification);

    }

}
