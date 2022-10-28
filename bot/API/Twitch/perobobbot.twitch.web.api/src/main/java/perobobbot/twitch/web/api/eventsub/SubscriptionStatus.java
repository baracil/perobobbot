package perobobbot.twitch.web.api.eventsub;

import fpc.tools.lang.IdentifiedEnum;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum SubscriptionStatus implements IdentifiedEnum {
    ENABLED("enabled",false),
    WEBHOOK_CALLBACK_VERIFICATION_PENDING("webhook_callback_verification_pending",false),
    WEBHOOK_CALLBACK_VERIFICATION_FAILED("webhook_callback_verification_failed",true),
    NOTIFICATION_FAILURES_EXCEEDED("notification_failures_exceeded",true),
    AUTHORIZATION_REVOKED("authorization_revoked",true),
    USER_REMOVED("user_removed",true),
    ;

    @Getter
    private final @NonNull String identification;

    @Getter
    private final boolean failure;
}
