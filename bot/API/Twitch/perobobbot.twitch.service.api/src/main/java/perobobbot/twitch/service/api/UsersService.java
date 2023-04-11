package perobobbot.twitch.service.api;

import lombok.NonNull;
import perobobbot.twitch.api.User;

import java.util.Set;

public interface UsersService {

    @AppAuth
    @NonNull Set<User> getUsers(@NonNull Set<String> ids);

    @AppAuth
    @NonNull User getUser(@NonNull String id);

}
