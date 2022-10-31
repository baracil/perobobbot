package perobobbot.twitch.web.client;

import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.annotation.*;
import io.micronaut.http.client.annotation.Client;
import lombok.NonNull;
import perobobbot.twitch.api.Twitch;
import perobobbot.twitch.api.eventsub.TwitchSubscriptionData;
import perobobbot.twitch.api.eventsub.TwitchSubscriptionRequest;

@Client(Twitch.HELIX_URL)
public interface EventSubClient {

    @Post("/eventsub/subscriptions")
    @NonNull TwitchSubscriptionData createEventSub(@NonNull @Body TwitchSubscriptionRequest parameters);

    @Delete("/eventsub/subscriptions")
    void delete(@NonNull @QueryValue(value = "id") String id);

    @Get("/eventsub/subscriptions{?parameter*}")
    @NonNull TwitchSubscriptionData getEventSubs(@Nullable GetEventSubParameter parameter);

    @Get("/eventsub/subscriptions")
    default @NonNull TwitchSubscriptionData getEventSubs() {
        return getEventSubs(null);
    }


}
