package perobobbot.twitch.eventsub;

import io.micronaut.http.HttpRequest;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public enum TwitchEventSubConstant {
    TWITCH_EVENTSUB_MESSAGE_ID("Twitch-Eventsub-Message-Id"),
    TWITCH_EVENTSUB_MESSAGE_RETRY("Twitch-Eventsub-Message-Retry"),
    TWITCH_EVENTSUB_MESSAGE_TYPE("Twitch-Eventsub-Message-Type"),
    TWITCH_EVENTSUB_MESSAGE_SIGNATURE("Twitch-Eventsub-Message-Signature"),
    TWITCH_EVENTSUB_MESSAGE_TIMESTAMP("Twitch-Eventsub-Message-Timestamp"),
    TWITCH_EVENTSUB_SUBSCRIPTION_TYPE("Twitch-Eventsub-Subscription-Type"),
    TWITCH_EVENTSUB_SUBSCRIPTION_VERSION("Twitch-Eventsub-Subscription-Version"),
    ;

    private final String headerKey;

    public Optional<String> getHeader(HttpRequest<?> request) {
        return request.getHeaders().getFirst(this.headerKey,String.class);
    }

}
