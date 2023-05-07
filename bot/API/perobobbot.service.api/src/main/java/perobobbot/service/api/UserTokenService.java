package perobobbot.service.api;

import fpc.tools.lang.Secret;
import perobobbot.api.data.*;

import java.util.Optional;

public interface UserTokenService {

    Optional<UserToken.Decrypted> findUserToken(Platform platform, String userId);

    Optional<RefreshTokenInfo<Secret>> findUserRefreshToken(Platform platform, String userId);

    void deleteUserToken(Platform platform, String userId);

    default UserToken.Decrypted getUserToken(Platform platform, String userId) {
        return findUserToken(platform,userId).orElseThrow(() -> new UserTokenDoesNotExist(platform,userId));
    }


    void setUserToken(TokenWithIdentity tokenWithIdentity);
}
