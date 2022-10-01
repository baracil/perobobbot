package perobobbot.web.controller;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.api.oauth.AuthHolder;
import perobobbot.api.oauth.OAuthDataFactory;
import perobobbot.service.api.UserIdentityService;
import perobobbot.twitch.api.Channel;
import perobobbot.twitch.api.Twitch;
import perobobbot.twitch.service.api.ChannelService;
import perobobbot.web.api.WebService;

import java.util.Optional;

@Controller(WebService.ROOT_PATH+"/test")
@RequiredArgsConstructor
public class TestController implements WebService {

    private final @NonNull ChannelService channelService;
    private final @NonNull UserIdentityService userIdentityService;
    private final @NonNull AuthHolder authHolder;
    private final @NonNull OAuthDataFactory oAuthDataFactory;

    @Get("/{login}")
    @NonNull Optional<Channel> getChannel(@NonNull @PathVariable String login) {
        final var userIdentity = userIdentityService.getByPlatformAndLogin(Twitch.PLATFORM,login);

        final var data = oAuthDataFactory.create(userIdentity);

        return authHolder.callWith(data, () -> channelService.getChannelInformation(userIdentity.userId()));
    }
}
