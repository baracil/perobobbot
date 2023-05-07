package perobobbot.oauth.api;

import perobobbot.api.data.TokenWithIdentity;

import java.net.URI;

public interface AuthorizationCodeGranFlow {

    URI getUri();

    String getState();

    void completeWithError(Failure failure);

    void completWithSuccess(TokenWithIdentity token);
}
