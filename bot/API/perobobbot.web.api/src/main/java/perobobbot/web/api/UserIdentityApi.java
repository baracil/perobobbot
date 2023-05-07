package perobobbot.web.api;


import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.annotation.*;
import perobobbot.api.data.JoinChannelParameters;
import perobobbot.api.data.JoinedChannel;
import perobobbot.api.data.Platform;
import perobobbot.api.data.UserIdentity;

import java.util.List;

public interface UserIdentityApi extends WebService {

    String PATH = ROOT_PATH+"/identity";

    @Get()
    List<UserIdentity> listUserIdentity(@Nullable @QueryValue Platform platform, @QueryValue(defaultValue = "0") int page, @QueryValue(defaultValue = "-1") int size);

    @Get("/{userIdentityId}")
    UserIdentity getUserIdentity(@PathVariable  long userIdentityId);

    @Get("/{userIdentityId}/channel")
    List<JoinedChannel> listJoinedChannels(@PathVariable long userIdentityId);

    @Get("/{userIdentityId}/channel/{channelName}")
    JoinedChannel getJoinedChannel(@PathVariable long userIdentityId, @PathVariable String channelName);

    @Delete("/{userIdentityId}/channel/{channelName}")
    void partChannel(@PathVariable long userIdentityId, @PathVariable String channelName);

    @Put("/{userIdentityId}/channel/{channelName}")
    JoinedChannel joinChannel(@PathVariable long userIdentityId,
                                       @PathVariable String channelName,
                                       @Body JoinChannelParameters parameters);



}
