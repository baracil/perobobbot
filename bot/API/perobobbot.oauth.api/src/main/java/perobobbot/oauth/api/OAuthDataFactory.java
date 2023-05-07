package perobobbot.oauth.api;

import perobobbot.api.UserInfo;

public interface OAuthDataFactory {

    OAuthData create(UserInfo userInfo);

}
