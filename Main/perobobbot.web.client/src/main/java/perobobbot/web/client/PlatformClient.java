package perobobbot.web.client;


import io.micronaut.http.client.annotation.Client;
import perobobbot.web.api.PlatformApi;

import static perobobbot.web.client.PlatformClient.SERVICE_ID;

@Client(id = SERVICE_ID, path = PlatformApi.PATH)
public interface PlatformClient extends PlatformApi {


}
