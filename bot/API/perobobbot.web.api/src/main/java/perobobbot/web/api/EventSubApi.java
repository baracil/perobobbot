package perobobbot.web.api;

import com.google.common.collect.ImmutableList;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.annotation.*;
import lombok.NonNull;
import perobobbot.api.SubscriptionView;
import perobobbot.api.data.Platform;
import perobobbot.service.api.CreateSubscriptionParameters;
import perobobbot.service.api.PatchSubscriptionParameters;
import perobobbot.service.api.SynchronizationParameters;

public interface EventSubApi extends WebService {

    String PATH = ROOT_PATH+"/eventsub";

    @Get
    @NonNull ImmutableList<SubscriptionView>  listEventSubs(
            @QueryValue(value = "platform") @Nullable Platform platform,
            @QueryValue(value = "page", defaultValue = "0") int page,
            @QueryValue(value = "size", defaultValue = "-1") int size
    );

    @Get("/{id}")
    @NonNull SubscriptionView getEventSubs(@PathVariable long id);

    @Delete("/{id}")
    void deleteEventSubs(@PathVariable long id);


    @Patch("/{id}")
    @NonNull SubscriptionView updateEventSubs(@PathVariable long id, @NonNull @Body PatchSubscriptionParameters parameters);

    @Post
    @NonNull SubscriptionView createSubscription(@NonNull @Body CreateSubscriptionParameters parameters);

    @Put
    void synchronizeSubscriptions(@NonNull @Body SynchronizationParameters parameters);


}
