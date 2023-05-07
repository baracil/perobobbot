package perobobbot.twitch.service.api;

import io.micronaut.retry.annotation.Fallback;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import perobobbot.api.UserInfo;
import perobobbot.api.data.Platform;
import perobobbot.api.data.PlatformUserInfoProvider;
import perobobbot.twitch.api.Twitch;

@Singleton
@Fallback
@RequiredArgsConstructor
public class FallbackUserInfoProvider implements PlatformUserInfoProvider {

    private final UsersService usersService;

    @Override
    public Platform getPlatform() {
        return Twitch.PLATFORM;
    }

    @Override
    public UserInfo getUserInfo(String userId) {
        final var user = usersService.getUser(userId);
        return user.toUserInfo();
    }
}
