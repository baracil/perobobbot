package perobobbot.oauth.api;

import perobobbot.api.Id;
import perobobbot.api.data.Platform;

public interface AuthHolder {

    OAuthData get(Platform platform);

    void executeWith(Id id, Runnable action);

    void setId(Id id);

    void clearId(Platform platform);

}
