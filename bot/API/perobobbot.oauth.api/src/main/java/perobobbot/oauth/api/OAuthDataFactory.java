package perobobbot.oauth.api;

import lombok.NonNull;
import perobobbot.api.UserInfo;

public interface OAuthDataFactory {

    @NonNull OAuthData create(@NonNull UserInfo userInfo);

}
