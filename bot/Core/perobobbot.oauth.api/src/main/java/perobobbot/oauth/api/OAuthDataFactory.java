package perobobbot.oauth.api;

import lombok.NonNull;
import perobobbot.api.data.UserIdentity;

public interface OAuthDataFactory {

    @NonNull OAuthData create(@NonNull UserIdentity userIdentity);

}
