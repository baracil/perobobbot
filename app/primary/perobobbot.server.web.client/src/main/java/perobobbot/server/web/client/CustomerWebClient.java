package perobobbot.server.web.client;


import io.micronaut.http.client.annotation.Client;
import perobobbot.server.web.api.CustomerWebApi;

@Client(id = "customer",path = "/api/v1/customer")
public interface CustomerWebClient extends CustomerWebApi {
}
