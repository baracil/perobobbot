package perobobbot.twitch.service.api;

import io.micronaut.retry.annotation.Fallback;
import jakarta.inject.Singleton;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.api.UserInfo;
import perobobbot.api.data.Platform;
import perobobbot.api.data.PlatformUserInfoProvider;
import perobobbot.twitch.api.Twitch;

@Singleton
@Fallback
@RequiredArgsConstructor
public class FallbackUserInfoProvider implements PlatformUserInfoProvider {

    private final @NonNull UsersService usersService;

    @Override
    public @NonNull Platform getPlatform() {
        return Twitch.PLATFORM;
    }

    @Override
    public @NonNull UserInfo getUserInfo(@NonNull String userId) {
        final var user = usersService.getUser(userId);
        return user.toUserInfo();
    }
}
