package perobobbot.service.api;

import lombok.NonNull;
import perobobbot.api.data.Platform;
import perobobbot.api.data.UserTokenDoesNotExist;
import perobobbot.api.data.view.UserToken;

import java.util.Optional;

public interface UserTokenService {

    @NonNull Optional<UserToken.Decrypted> findUserToken(@NonNull Platform platform);

    @NonNull Optional<UserToken.Decrypted> findUserToken(@NonNull Platform platform, @NonNull String login);

    @NonNull Optional<UserToken.Decrypted> findMainUserToken(@NonNull Platform platform);

    void deleteUserToken(@NonNull Platform platform);

    default @NonNull UserToken.Decrypted getMainUserToken(@NonNull Platform platform) {
        return findMainUserToken(platform).orElseThrow(() -> new UserTokenDoesNotExist(platform));
    }

    default @NonNull UserToken.Decrypted getUserToken(@NonNull Platform platform) {
        return findUserToken(platform).orElseThrow(() -> new UserTokenDoesNotExist(platform));
    }

    default @NonNull UserToken.Decrypted getUserToken(@NonNull Platform platform, @NonNull String login) {
        return findUserToken(platform,login).orElseThrow(() -> new UserTokenDoesNotExist(platform));
    }

    void setUserToken(@NonNull UserToken.Decrypted userToken);
}
