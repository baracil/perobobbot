package perobobbot.service.jpa.repository;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;
import lombok.NonNull;
import perobobbot.api.Identification;
import perobobbot.api.data.Platform;
import perobobbot.api.data.UserType;
import perobobbot.service.api.UnknownLogin;
import perobobbot.service.api.UserNotFound;
import perobobbot.service.jpa.domain.UserIdentityEntity;

import java.util.Optional;

@Repository
public interface UserIdentityRepository extends JpaRepository<UserIdentityEntity, Long> {

    @NonNull Optional<UserIdentityEntity> findByPlatformAndUserId(@NonNull Platform platform, @NonNull String userId);

    @NonNull Optional<UserIdentityEntity> findByPlatformAndLogin(@NonNull Platform platform, @NonNull String login);

    @NonNull Optional<UserIdentityEntity> findByPlatformAndUserType(@NonNull Platform platform, @NonNull UserType userType);

    default @NonNull Optional<UserIdentityEntity> findByIdentification(@NonNull Identification identification) {
        return findByPlatformAndUserId(identification.platform(), identification.userId());
    }

    default @NonNull UserIdentityEntity getByIdentification(@NonNull Identification identification) {
        return findByIdentification(identification).orElseThrow(() -> new UserNotFound(identification));
    }

    default @NonNull UserIdentityEntity getByPlatformAndLogin(@NonNull Platform platform, @NonNull String login) {
        return findByPlatformAndLogin(platform,login).orElseThrow(() -> new UnknownLogin(platform,login));
    }

}
