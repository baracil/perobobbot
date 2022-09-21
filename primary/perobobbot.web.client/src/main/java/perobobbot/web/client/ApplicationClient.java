package perobobbot.web.client;


import io.micronaut.http.client.annotation.Client;
import perobobbot.web.api.ApplicationApi;

import static perobobbot.web.client.ApplicationClient.SERVICE_ID;

@Client(id = SERVICE_ID, path = ApplicationApi.PATH)
public interface ApplicationClient extends ApplicationApi {


}
