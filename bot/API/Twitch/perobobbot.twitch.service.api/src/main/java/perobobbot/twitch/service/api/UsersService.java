package perobobbot.twitch.service.api;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import perobobbot.twitch.api.User;

import java.util.Set;

public interface UsersService {

    @AppAuth
    @NonNull ImmutableSet<User> getUsers(@NonNull Set<String> ids);

    @AppAuth
    @NonNull User getUser(@NonNull String id);

}
