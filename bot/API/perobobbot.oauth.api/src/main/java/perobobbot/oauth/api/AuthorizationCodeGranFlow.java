package perobobbot.oauth.api;

import lombok.NonNull;
import perobobbot.api.data.TokenWithIdentity;

import java.net.URI;
import java.util.function.Consumer;

public interface AuthorizationCodeGranFlow {

    @NonNull URI getUri();

    void whenComplete(Consumer<TokenWithIdentity> onResult, @NonNull Consumer<Throwable> onError);

    void setFailed(@NonNull Failure failure);
}
