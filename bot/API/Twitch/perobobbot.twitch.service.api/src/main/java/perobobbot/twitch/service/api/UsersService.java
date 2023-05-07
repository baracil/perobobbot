package perobobbot.twitch.service.api;

import perobobbot.twitch.api.User;

import java.util.Set;

public interface UsersService {

    @AppAuth
    Set<User> getUsers(Set<String> ids);

    @AppAuth
    User getUser(String id);

}
