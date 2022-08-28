package perobobbot.data.web.client;


import io.micronaut.http.client.annotation.Client;
import perobobbot.data.web.api.CustomerWebApi;

import static perobobbot.data.web.client.CustomerWebClient.SERVICE_ID;

@Client(id = SERVICE_ID, path = CustomerWebApi.PATH)
public interface CustomerWebClient extends CustomerWebApi {

}
