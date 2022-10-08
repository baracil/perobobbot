package perobobbot.service.jpa.repository;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import lombok.NonNull;
import perobobbot.api.Identification;
import perobobbot.api.data.Platform;
import perobobbot.api.data.UnknownUserIdentityId;
import perobobbot.api.data.UserIdentityType;
import perobobbot.service.jpa.domain.UserIdentityEntity;

import java.util.Optional;
import java.util.stream.Stream;

@Repository
public interface UserIdentityRepository extends JpaRepository<UserIdentityEntity, Long> {

    @NonNull Page<UserIdentityEntity> findByPlatform(@NonNull Platform platform, @NonNull Pageable pageable);

    @NonNull Optional<UserIdentityEntity> findByPlatformAndUserId(@NonNull Platform platform, @NonNull String userId);

    @NonNull Optional<UserIdentityEntity> findByPlatformAndUserIdentityType(@NonNull Platform platform, @NonNull UserIdentityType userIdentityId);

    @NonNull Stream<UserIdentityEntity> findByUserIdentityType(@NonNull UserIdentityType userIdentityType);

    default @NonNull UserIdentityEntity getById(long userIdentityId) {
        return findById(userIdentityId).orElseThrow(() -> new UnknownUserIdentityId(userIdentityId));
    }


    default @NonNull Optional<UserIdentityEntity> findByIdentification(@NonNull Identification identification) {
        return findByPlatformAndUserId(identification.platform(), identification.userId());
    }

}
