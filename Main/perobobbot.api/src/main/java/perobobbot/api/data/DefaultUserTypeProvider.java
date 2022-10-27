package perobobbot.api.data;

import io.micronaut.retry.annotation.Fallback;
import jakarta.inject.Singleton;
import lombok.NonNull;
import perobobbot.api.UserInfo;

@Singleton
@Fallback
public class DefaultUserTypeProvider implements UserTypeProvider {

    public static final Platform TWITCH = new Platform("TWITCH");

    @Override
    public @NonNull UserType getUserType(@NonNull UserInfo userInfo) {
        if (userInfo.platform().equals(TWITCH)) {
            return getTwitchUserType(userInfo);
        }
        return UserType.VIEWER;
    }

    private UserType getTwitchUserType(UserInfo userInfo) {
        return switch (userInfo.login().toLowerCase()) {
            case "perococco" -> UserType.BROADCASTER;
            case "perobobbot" -> UserType.BOT;
            default -> UserType.VIEWER;
        };
    }
}
