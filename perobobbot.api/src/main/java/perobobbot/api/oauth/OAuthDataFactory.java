package perobobbot.api.oauth;

import lombok.NonNull;
import perobobbot.api.data.view.UserIdentity;

public interface OAuthDataFactory {

    @NonNull OAuthData create(@NonNull UserIdentity userIdentity);

}
