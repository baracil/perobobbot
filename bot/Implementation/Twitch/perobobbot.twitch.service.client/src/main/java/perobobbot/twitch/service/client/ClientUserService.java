package perobobbot.twitch.service.client;

import com.google.common.collect.ImmutableSet;
import jakarta.inject.Singleton;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.api.PerobobbotException;
import perobobbot.twitch.api.User;
import perobobbot.twitch.service.api.UsersService;
import perobobbot.twitch.web.client.UserClient;

import java.util.List;
import java.util.Set;

@Singleton
@RequiredArgsConstructor
public class ClientUserService implements UsersService {

    private final UserClient userClient;

    @Override
    public @NonNull ImmutableSet<User> getUsers(@NonNull Set<String> ids) {
        final var users = doGetUsers(ids);
        return ImmutableSet.copyOf(users);
    }

    @Override
    public @NonNull User getUser(@NonNull String id) {
        final var users = doGetUsers(Set.of(id));
        return users.stream().findFirst().orElseThrow(() -> new PerobobbotException("No Twitch user with id '" + id + "' could be found"));
    }


    private @NonNull List<User> doGetUsers(@NonNull Set<String> ids) {
        final var users = userClient.getUsers(ids, null).getData();

        if (users == null) {
            return List.of();
        }
        return users;
    }

}
