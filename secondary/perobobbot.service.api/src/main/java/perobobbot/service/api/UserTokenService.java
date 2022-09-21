package perobobbot.service.api;

import lombok.NonNull;
import perobobbot.api.data.Platform;
import perobobbot.api.data.UserTokenDoesNotExist;
import perobobbot.api.data.view.UserToken;

import java.util.Optional;

public interface UserTokenService {


    @NonNull Optional<UserToken> findUserToken(@NonNull Platform platform);

    void deleteUserToken(@NonNull Platform platform);

    default @NonNull UserToken getUserToken(@NonNull Platform platform) {
        return findUserToken(platform).orElseThrow(() -> new UserTokenDoesNotExist(platform));
    }

    void setUserToken(@NonNull UserToken token);
}
