package perobobbot.data.web.client;


import io.micronaut.http.client.annotation.Client;
import perobobbot.data.web.api.ApplicationWebApi;

import static perobobbot.data.web.client.ApplicationWebClient.SERVICE_ID;

@Client(id = SERVICE_ID, path = ApplicationWebApi.PATH)
public interface ApplicationWebClient extends ApplicationWebApi {

}
