package perobobbot.data.web.client;


import io.micronaut.http.client.annotation.Client;
import perobobbot.data.web.api.CustomerWebApi;

@Client(id = "customer",path = "/api/v1/customer")
public interface CustomerWebClient extends CustomerWebApi {
}
