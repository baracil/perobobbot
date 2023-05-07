package perobobbot.web.api;

import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.annotation.*;
import perobobbot.api.SubscriptionView;
import perobobbot.api.data.Platform;
import perobobbot.service.api.CreateSubscriptionParameters;
import perobobbot.service.api.PatchSubscriptionParameters;
import perobobbot.service.api.SynchronizationParameters;

import java.util.List;

public interface EventSubApi extends WebService {

    String PATH = ROOT_PATH+"/eventsub";

    @Get
    List<SubscriptionView> listEventSubs(
            @QueryValue(value = "platform") @Nullable Platform platform,
            @QueryValue(value = "page", defaultValue = "0") int page,
            @QueryValue(value = "size", defaultValue = "-1") int size
    );

    @Get("/{id}")
    SubscriptionView getEventSubs(@PathVariable long id);

    @Delete("/{id}")
    void deleteEventSubs(@PathVariable long id);


    @Patch("/{id}")
    SubscriptionView updateEventSubs(@PathVariable long id, @Body PatchSubscriptionParameters parameters);

    @Post
    SubscriptionView createSubscription(@Body CreateSubscriptionParameters parameters);

    @Put
    void synchronizeSubscriptions(@Body SynchronizationParameters parameters);


}
