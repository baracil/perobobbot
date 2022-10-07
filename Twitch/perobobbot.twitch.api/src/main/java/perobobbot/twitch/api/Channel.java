package perobobbot.twitch.api;

import io.micronaut.serde.annotation.Serdeable;
import lombok.NonNull;
import perobobbot.api.UserInfo;

@Serdeable
public record Channel(@NonNull UserInfo broadcaster,
                      @NonNull Game game,
                      @NonNull String broadcasterLanguage,
                      @NonNull String title, int delay) {
}
