package perobobbot.twitch.web.client;

import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.http.client.annotation.Client;
import perobobbot.twitch.api.Twitch;
import perobobbot.twitch.api.Users;

import java.util.Set;

@Client(Twitch.HELIX_URL)
public interface UserClient {

    @Get("users{?id,login}")
    Users getUsers(@Nullable @QueryValue Set<String> id, @Nullable @QueryValue Set<String> login);


}
