package perobobbot.twitch.service.client;

import jakarta.inject.Singleton;
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
    public Set<User> getUsers(Set<String> ids) {
        final var users = doGetUsers(ids);
        return Set.copyOf(users);
    }

    @Override
    public User getUser(String id) {
        final var users = doGetUsers(Set.of(id));
        return users.stream().findFirst().orElseThrow(() -> new PerobobbotException("No Twitch user with id '" + id + "' could be found"));
    }


    private List<User> doGetUsers(Set<String> ids) {
        final var users = userClient.getUsers(ids, null).getData();

        if (users == null) {
            return List.of();
        }
        return users;
    }

}
