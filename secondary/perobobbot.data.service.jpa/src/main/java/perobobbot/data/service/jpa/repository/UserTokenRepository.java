package perobobbot.data.service.jpa.repository;

import io.micronaut.data.jpa.repository.JpaRepository;
import lombok.NonNull;
import perobobbot.data.service.jpa.domain.UserToken;

import java.util.Optional;

public interface UserTokenRepository extends JpaRepository<UserToken,Long> {

    @NonNull Optional<UserToken> findByPlatform(@NonNull String platform);
}
