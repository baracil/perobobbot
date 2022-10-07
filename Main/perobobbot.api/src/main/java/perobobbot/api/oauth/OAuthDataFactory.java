package perobobbot.api.oauth;

import lombok.NonNull;
import perobobbot.api.Identification;

public interface OAuthDataFactory {

    @NonNull OAuthData create(@NonNull Identification identification, @NonNull String login);

}
