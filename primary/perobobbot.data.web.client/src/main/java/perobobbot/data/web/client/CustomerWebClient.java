package perobobbot.data.web.client;


import io.micronaut.http.client.annotation.Client;
import perobobbot.data.web.api.CustomerWebApi;

@Client(id = "perobobbot-data",path = CustomerWebApi.PATH)
public interface CustomerWebClient extends CustomerWebApi {


}
