package perobobbot.domain.jpa.repository;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
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

    Page<UserIdentityEntity> findByPlatform(Platform platform, Pageable pageable);

    Optional<UserIdentityEntity> findByPlatformAndUserId(Platform platform, String userId);

    Optional<UserIdentityEntity> findByPlatformAndLoginIgnoreCase(Platform platform, String login);

    Optional<UserIdentityEntity> findByPlatformAndUserType(Platform platform, UserType userIdentityId);

    Stream<UserIdentityEntity> findByUserType(UserType userType);

    default UserIdentityEntity getById(long userIdentityId) {
        return findById(userIdentityId).orElseThrow(() -> new UnknownUserIdentityId(userIdentityId));
    }


    default Optional<UserIdentityEntity> findByIdentification(Identity identity) {
        return findByPlatformAndUserId(identity.platform(), identity.userId());
    }

    default UserIdentityEntity getByPlatformAndUserId(Platform platform, String userId) {
        return findByPlatformAndUserId(platform, userId).orElseThrow(() -> new UnknownUserId(new Identity(platform, userId)));
    }

}
