package perobobbot.service.jpa.repository;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;
import lombok.NonNull;
import perobobbot.api.data.Platform;
import perobobbot.service.jpa.domain.UserTokenEntity;

import java.util.Optional;

@Repository
public interface UserTokenRepository extends JpaRepository<UserTokenEntity,Long> {

    @NonNull Optional<UserTokenEntity> findByPlatform(@NonNull Platform platform);

    void deleteByPlatform(@NonNull Platform platform);

    @NonNull Optional<UserTokenEntity> findByPlatformAndUserIdentityLogin(@NonNull Platform platform, @NonNull String login);

    @NonNull Optional<UserTokenEntity> findByPlatformAndMainTrue(@NonNull Platform platform);
}
