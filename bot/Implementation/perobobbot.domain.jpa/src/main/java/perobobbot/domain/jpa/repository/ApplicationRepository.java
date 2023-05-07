package perobobbot.domain.jpa.repository;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;
import perobobbot.api.data.ApplicationForPlatformMissing;
import perobobbot.api.data.Platform;
import perobobbot.domain.jpa.entity.ApplicationEntity;

import java.util.Optional;
import java.util.stream.Stream;

@Repository
public interface ApplicationRepository extends JpaRepository<ApplicationEntity,Long> {

    Optional<String> findClientIdByPlatform(Platform platform);

    Optional<ApplicationEntity> findByPlatform(Platform platform);

    default ApplicationEntity getByPlatform(Platform platform) {
        return findByPlatform(platform).orElseThrow(() -> new ApplicationForPlatformMissing(platform));
    }

    default String getClientIdByPlatform(Platform platform) {
        return findClientIdByPlatform(platform).orElseThrow(() -> new ApplicationForPlatformMissing(platform));
    }

    Stream<Platform> findPlatform();


}
