package perobobbot.data.web.client;


import io.micronaut.http.client.annotation.Client;
import perobobbot.data.web.api.ApplicationApi;

import static perobobbot.data.web.client.ApplicationClient.SERVICE_ID;

@Client(id = SERVICE_ID, path = ApplicationApi.PATH)
public interface ApplicationClient extends ApplicationApi {

}
