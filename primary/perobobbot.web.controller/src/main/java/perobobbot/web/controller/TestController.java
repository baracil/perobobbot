package perobobbot.web.controller;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.api.oauth.AuthData;
import perobobbot.api.oauth.AuthHolder;
import perobobbot.service.api.ApplicationService;
import perobobbot.service.api.UserTokenService;
import perobobbot.twitch.api.Channel;
import perobobbot.twitch.api.Twitch;
import perobobbot.twitch.service.api.ChannelService;
import perobobbot.web.api.WebService;

import java.util.Optional;

@Controller(WebService.ROOT_PATH+"/test")
@RequiredArgsConstructor
public class TestController implements WebService {

    private final @NonNull ChannelService channelService;
    private final @NonNull UserTokenService userTokenService;
    private final @NonNull ApplicationService applicationService;
    private final @NonNull AuthHolder authHolder;

    @Get("/{login}")
    @NonNull Optional<Channel> getChannel(@NonNull @PathVariable String login) {
        final var application = applicationService.getApplication(Twitch.PLATFORM);
        final var userToken = userTokenService.getUserToken(Twitch.PLATFORM,login);
        final var appToken = applicationService.findApplicationToken(Twitch.PLATFORM).orElse(null);

        return authHolder.callWith(new AuthData(application,userToken,appToken), () -> channelService.getChannelInformation(userToken.identity().userId()));
    }
}
