package perobobbot.oauth;

import lombok.NonNull;
import perobobbot.api.data.view.UserToken;

import java.net.URI;
import java.util.function.Consumer;

public interface AuthorizationCodeGranFlow {

    @NonNull URI getUri();

    void whenComplete(Consumer<UserToken> onResult, @NonNull Consumer<Throwable> onError);

    void setFailed(@NonNull Failure failure);
}
