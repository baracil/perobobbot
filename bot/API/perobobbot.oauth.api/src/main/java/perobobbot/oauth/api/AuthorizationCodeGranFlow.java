package perobobbot.oauth.api;

import lombok.NonNull;
import perobobbot.api.data.TokenWithIdentity;

import java.net.URI;

public interface AuthorizationCodeGranFlow {

    @NonNull URI getUri();

    @NonNull String getState();

    void completeWithError(@NonNull Failure failure);

    void completWithSuccess(@NonNull TokenWithIdentity token);
}
