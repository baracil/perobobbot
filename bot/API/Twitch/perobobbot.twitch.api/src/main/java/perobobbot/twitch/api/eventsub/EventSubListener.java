package perobobbot.twitch.api.eventsub;

import lombok.NonNull;

public interface EventSubListener {

    void handleNotification(@NonNull EventSubNotification notification);

    default void handleRevocation(@NonNull EventSubRevocation revocation) {}

    default void handleVerification(@NonNull EventSubVerification verification) {}


}
