package perobobbot.api.data;

import io.micronaut.retry.annotation.Fallback;
import jakarta.inject.Singleton;
import lombok.NonNull;
import perobobbot.api.UserInfo;

@Singleton
@Fallback
public class DefaultUserTypeProvider implements UserTypeProvider {

    @Override
    public @NonNull UserIdentityType getUserType(@NonNull UserInfo userInfo) {
        if (userInfo.platform().equals(new Platform("TWITCH"))) {
            return getTwitchUserType(userInfo);
        }
        return UserIdentityType.VIEWER;
    }

    private UserIdentityType getTwitchUserType(UserInfo userInfo) {
        return switch (userInfo.login().toLowerCase()) {
            case "perococco" -> UserIdentityType.BROADCASTER;
            case "perobobbot" -> UserIdentityType.BOT;
            default -> UserIdentityType.VIEWER;
        };
    }
}
