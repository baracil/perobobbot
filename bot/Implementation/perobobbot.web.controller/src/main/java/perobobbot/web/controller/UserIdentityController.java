package perobobbot.web.controller;

import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import lombok.RequiredArgsConstructor;
import perobobbot.api.data.JoinChannelParameters;
import perobobbot.api.data.JoinedChannel;
import perobobbot.api.data.Platform;
import perobobbot.api.data.UserIdentity;
import perobobbot.service.api.UserIdentityService;
import perobobbot.web.api.UserIdentityApi;

import java.util.List;

@Controller(UserIdentityApi.PATH)
@RequiredArgsConstructor
@ExecuteOn(TaskExecutors.IO)
public class UserIdentityController implements UserIdentityApi {

    private final UserIdentityService userIdentityService;

    @Override
    public List<UserIdentity> listUserIdentity(@Nullable @QueryValue Platform platform,
                                                        @QueryValue(defaultValue = "0") int page,
                                                        @QueryValue(defaultValue = "-1") int size) {
        if (platform == null) {
            return userIdentityService.listUserIdentities(page,size);
        } else {
            return userIdentityService.listUserIdentities(platform,page,size);
        }
    }

    @Override
    public List<JoinedChannel> listJoinedChannels(@PathVariable long userIdentityId) {
        final var userIdentity = userIdentityService.getUserIdentity(userIdentityId);
        return userIdentity.joinedChannels();
    }

    @Override
    public UserIdentity getUserIdentity(@PathVariable long userIdentityId) {
        return userIdentityService.getUserIdentity(userIdentityId);
    }

    @Override
    public JoinedChannel getJoinedChannel(@PathVariable long userIdentityId, @PathVariable String channelName) {
        return userIdentityService.getJoinedChannel(userIdentityId,channelName);

    }

    @Override
    public void partChannel(@PathVariable long userIdentityId, @PathVariable String channelName) {
        userIdentityService.partChannel(userIdentityId,channelName);
    }

    @Override
    public JoinedChannel joinChannel(@PathVariable long userIdentityId, @PathVariable String channelName, @Body JoinChannelParameters parameters) {
        return userIdentityService.joinChannel(userIdentityId,channelName, parameters.readOnly());
    }
}
