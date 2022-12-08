package perobobbot.domain.jpa.repository;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import lombok.NonNull;
import perobobbot.api.Identity;
import perobobbot.api.data.Platform;
import perobobbot.api.data.UnknownUserId;
import perobobbot.api.data.UnknownUserIdentityId;
import perobobbot.api.data.UserType;
import perobobbot.domain.jpa.entity.UserIdentityEntity;

import java.util.Optional;
import java.util.stream.Stream;

@Repository
public interface UserIdentityRepository extends JpaRepository<UserIdentityEntity, Long> {

    @NonNull Page<UserIdentityEntity> findByPlatform(@NonNull Platform platform, @NonNull Pageable pageable);

    @NonNull Optional<UserIdentityEntity> findByPlatformAndUserId(@NonNull Platform platform, @NonNull String userId);

    @NonNull Optional<UserIdentityEntity> findByPlatformAndLoginIgnoreCase(@NonNull Platform platform, @NonNull String login);

    @NonNull Optional<UserIdentityEntity> findByPlatformAndUserType(@NonNull Platform platform, @NonNull UserType userIdentityId);

    @NonNull Stream<UserIdentityEntity> findByUserType(@NonNull UserType userType);

    default @NonNull UserIdentityEntity getById(long userIdentityId) {
        return findById(userIdentityId).orElseThrow(() -> new UnknownUserIdentityId(userIdentityId));
    }


    default @NonNull Optional<UserIdentityEntity> findByIdentification(@NonNull Identity identity) {
        return findByPlatformAndUserId(identity.platform(), identity.userId());
    }

    default @NonNull UserIdentityEntity getByPlatformAndUserId(@NonNull Platform platform, @NonNull String userId) {
        return findByPlatformAndUserId(platform, userId).orElseThrow(() -> new UnknownUserId(new Identity(platform, userId)));
    }

}
