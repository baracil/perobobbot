package perobobbot.web.controller;

import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.QueryValue;
import jakarta.annotation.Nullable;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.api.data.JoinChannelParameters;
import perobobbot.api.data.JoinedChannel;
import perobobbot.api.data.Platform;
import perobobbot.api.data.view.UserIdentity;
import perobobbot.service.api.UserIdentityService;
import perobobbot.web.api.UserIdentityApi;

import java.util.List;
import java.util.Optional;

@Controller(UserIdentityApi.PATH)
@RequiredArgsConstructor
public class UserIdentityController implements UserIdentityApi {

    private final @NonNull UserIdentityService userIdentityService;

    @Override
    public @NonNull List<UserIdentity> listUserIdentity(@Nullable @QueryValue Optional<Platform> platform,
                                                        @QueryValue(defaultValue = "0") int page,
                                                        @QueryValue(defaultValue = "-1") int size) {
        return platform.map(p -> userIdentityService.listUserIdentities(p,page,size))
                .orElseGet(() -> userIdentityService.listUserIdentities(page,size));
    }

    @Override
    public @NonNull List<JoinedChannel> listJoinedChannels(@PathVariable long userIdentityId) {
        final var userIdentity = userIdentityService.getUserIdentity(userIdentityId);
        return userIdentity.joinedChannels();
    }

    @Override
    public @NonNull UserIdentity getUserIdentity(@PathVariable long userIdentityId) {
        return userIdentityService.getUserIdentity(userIdentityId);
    }

    @Override
    public @NonNull JoinedChannel getJoinedChannel(@PathVariable long userIdentityId, @PathVariable @NonNull String channelName) {
        return userIdentityService.getJoinedChannel(userIdentityId,channelName);

    }

    @Override
    public void partChannel(@PathVariable long userIdentityId, @PathVariable @NonNull String channelName) {
        userIdentityService.partChannel(userIdentityId,channelName);
    }

    @Override
    public @NonNull JoinedChannel joinChannel(@PathVariable long userIdentityId, @PathVariable @NonNull String channelName, @Body @NonNull JoinChannelParameters parameters) {
        return userIdentityService.joinChannel(userIdentityId,channelName, parameters.readOnly());
    }
}
