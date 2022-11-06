package perobobbot.twitch.eventsub.callback;

import fpc.tools.lang.Instants;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.twitch.eventsub.SeenEventSubId;
import perobobbot.twitch.eventsub.TwitchEventSubConstant;

import java.time.Duration;
import java.time.Instant;

/**
 * Filter duplicate event sub notifications.
 * Pass nofitication not seen
 */
@RequiredArgsConstructor
//@Singleton
public class DuplicateFilterHandler implements EventSubHandler {

    public static final Duration DELAY_FOR_INVALIDITY = Duration.ofMinutes(10);

    private final @NonNull Instants clock;

    private final SeenEventSubId seenIds;

    @Override
    public int priority() {
        return 100;
    }

    @Override
    public @NonNull HttpResponse<?> handleCall(@NonNull HttpRequest<?> request, @NonNull String body, @NonNull CallContext context) {
        final var timeStamp = TwitchEventSubConstant.TWITCH_EVENTSUB_MESSAGE_TIMESTAMP.getHeader(request)
                                                                                      .map(this::parseUTC)
                                                                                      .orElse(null);
        final var id = TwitchEventSubConstant.TWITCH_EVENTSUB_MESSAGE_ID.getHeader(request).orElse(null);

        if (timeStamp == null || id == null) {
            return HttpResponse.notFound();
        }

        final var invalidityTimestamp = timeStamp.plus(DELAY_FOR_INVALIDITY);
        if (invalidityTimestamp.isBefore(clock.now())) {
            return HttpResponse.notFound();
        }

        if (seenIds.notAlreadySeen(id)) {
            final var response = context.proceed(request, body);
            seenIds.setSeen(id);
            return response;
        }
        return HttpResponse.notFound();
    }


    private @NonNull Instant parseUTC(@NonNull String utcTimeStamp) {
        return Instant.parse(utcTimeStamp);
    }
}
