package perobobbot.web.api;


import io.micronaut.http.annotation.*;
import jakarta.annotation.Nullable;
import lombok.NonNull;
import perobobbot.api.data.JoinChannelParameters;
import perobobbot.api.data.JoinedChannel;
import perobobbot.api.data.Platform;
import perobobbot.api.data.UserIdentity;

import java.util.List;
import java.util.Optional;

public interface UserIdentityApi extends WebService {

    String PATH = ROOT_PATH+"/identity";

    @Get()
    @NonNull List<UserIdentity> listUserIdentity(@Nullable @QueryValue Optional<Platform> platform, @QueryValue(defaultValue = "0") int page, @QueryValue(defaultValue = "-1") int size);

    @Get("/{userIdentityId}")
    @NonNull UserIdentity getUserIdentity(@PathVariable  long userIdentityId);

    @Get("/{userIdentityId}/channel")
    @NonNull List<JoinedChannel> listJoinedChannels(@PathVariable long userIdentityId);

    @Get("/{userIdentityId}/channel/{channelName}")
    @NonNull JoinedChannel getJoinedChannel(@PathVariable long userIdentityId, @NonNull @PathVariable String channelName);

    @Delete("/{userIdentityId}/channel/{channelName}")
    void partChannel(@PathVariable long userIdentityId, @NonNull @PathVariable String channelName);

    @Put("/{userIdentityId}/channel/{channelName}")
    @NonNull JoinedChannel joinChannel(@PathVariable long userIdentityId,
                                       @NonNull @PathVariable String channelName,
                                       @NonNull @Body JoinChannelParameters parameters);



}
