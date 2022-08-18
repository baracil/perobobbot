package perobobbot.server.launcher;

import io.micronaut.http.client.annotation.Client;
import perobobbot.server.web.api.CustomerWebApi;

@Client("/customer")
public interface TestCustomerClient extends CustomerWebApi {
}
