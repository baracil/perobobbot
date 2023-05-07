package perobobbot.twitch.web.client;

import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.annotation.*;
import io.micronaut.http.client.annotation.Client;
import perobobbot.twitch.api.Twitch;
import perobobbot.twitch.api.eventsub.TwitchSubscriptionData;
import perobobbot.twitch.api.eventsub.TwitchSubscriptionRequest;

@Client(Twitch.HELIX_URL)
public interface EventSubClient {

    @Post("/eventsub/subscriptions")
    TwitchSubscriptionData createEventSub(@Body TwitchSubscriptionRequest parameters);

    @Delete("/eventsub/subscriptions")
    void delete(@QueryValue(value = "id") String id);

    @Get("/eventsub/subscriptions{?parameter*}")
    TwitchSubscriptionData getEventSubs(@Nullable GetEventSubParameter parameter);

    @Get("/eventsub/subscriptions")
    default TwitchSubscriptionData getEventSubs() {
        return getEventSubs(null);
    }


}
