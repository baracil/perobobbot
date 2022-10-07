package perobobbot.web.client;

import io.micronaut.http.client.annotation.Client;
import perobobbot.web.api.UserApi;

import static perobobbot.web.api.WebService.SERVICE_ID;

@Client(id = SERVICE_ID, path = UserApi.PATH)
public interface UserClient extends UserApi {
}
