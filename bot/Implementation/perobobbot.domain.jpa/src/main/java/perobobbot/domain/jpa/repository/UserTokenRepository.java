package perobobbot.domain.jpa.repository;

import io.micronaut.data.annotation.Query;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;
import lombok.NonNull;
import perobobbot.api.data.Platform;
import perobobbot.api.data.RefreshTokenInfo;
import perobobbot.domain.jpa.entity.UserTokenEntity;

import java.util.Optional;

@Repository
public interface UserTokenRepository extends JpaRepository<UserTokenEntity, Long> {

    @Query("DELETE FROM UserTokenEntity WHERE platform = :platform AND userIdentity.userId = :userId")
    void deleteByPlatformAndUserIdentityId(@NonNull Platform platform, @NonNull String userId);

    @Query("""
            SELECT t
            FROM UserTokenEntity t
            WHERE t.platform = :platform AND t.userIdentity.userId = :userId
            """)
    @NonNull Optional<UserTokenEntity> findByPlatformAndPlatformUserId(@NonNull Platform platform, @NonNull String userId);

    @Query("SELECT new perobobbot.api.data.RefreshTokenInfo$Encrypted(t.platform, t.refreshToken,t.expirationInstant) FROM UserTokenEntity t WHERE t.platform = :platform AND t.userIdentity.userId = :userId")
    Optional<RefreshTokenInfo<String>> findUserRefreshTokenInfo(@NonNull Platform platform, @NonNull String userId);
}
