package perobobbot.twitch.web.api.eventsub;

import lombok.NonNull;

public record ObjectWithLogin<T>(@NonNull String login, @NonNull T value) {

    public static <T> @NonNull ObjectWithLogin<T> create(@NonNull String login, @NonNull T value) {
        return new ObjectWithLogin<>(login,value);
    }

}
