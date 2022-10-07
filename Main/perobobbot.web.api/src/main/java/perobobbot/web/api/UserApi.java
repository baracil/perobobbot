package perobobbot.web.api;

import io.micronaut.http.annotation.*;
import lombok.NonNull;
import perobobbot.api.data.Channel;
import perobobbot.api.data.CreateChannelParameters;
import perobobbot.api.data.Platform;
import perobobbot.api.data.view.UserIdentity;

import java.util.List;

public interface UserApi extends WebService{

    String PATH = ROOT_PATH+"/data/{platform}/user";

    @Get()
    @NonNull List<UserIdentity> getUser(@NonNull @PathVariable Platform platform, @QueryValue(defaultValue = "0") int page, @QueryValue(defaultValue = "-1") int size);

    @Get("/{userId}")
    @NonNull UserIdentity getUser(@NonNull @PathVariable Platform platform, @PathVariable long userId);

    @Get("/{userId}/channel")
    @NonNull List<Channel> getUserChannel(@NonNull @PathVariable Platform platform, @PathVariable long userId);

    @Get("/{userId}/channel/{channelName}")
    @NonNull Channel getUserChannel(@NonNull @PathVariable Platform platform,  @PathVariable long userId, @NonNull @PathVariable String channelName);

    @Put("{userId}/channel/{channelName}")
    @NonNull Channel joinChannel(@PathVariable long userId, @NonNull @PathVariable String channelName, @NonNull @Body CreateChannelParameters parameters);
}
