package perobobbot.oauth.api;

import lombok.NonNull;
import perobobbot.api.Id;
import perobobbot.api.data.Platform;

public interface AuthHolder {

    @NonNull OAuthData get(@NonNull Platform platform);

    void executeWith(@NonNull Id id, @NonNull Runnable action);

    void setId(@NonNull Id id);

    void clearId(@NonNull Platform platform);

}
