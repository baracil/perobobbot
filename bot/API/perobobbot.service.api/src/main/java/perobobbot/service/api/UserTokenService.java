package perobobbot.service.api;

import fpc.tools.lang.Secret;
import lombok.NonNull;
import perobobbot.api.data.*;

import java.util.Optional;

public interface UserTokenService {

    @NonNull Optional<UserToken.Decrypted> findUserToken(@NonNull Platform platform, @NonNull String userId);

    @NonNull Optional<RefreshTokenInfo<Secret>> findUserRefreshToken(@NonNull Platform platform, @NonNull String userId);

    void deleteUserToken(@NonNull Platform platform, @NonNull String userId);

    default @NonNull UserToken.Decrypted getUserToken(@NonNull Platform platform, @NonNull String userId) {
        return findUserToken(platform,userId).orElseThrow(() -> new UserTokenDoesNotExist(platform,userId));
    }


    void setUserToken(@NonNull TokenWithIdentity tokenWithIdentity);
}
