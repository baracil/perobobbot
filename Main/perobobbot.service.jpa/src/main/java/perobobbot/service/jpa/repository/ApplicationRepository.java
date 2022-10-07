package perobobbot.service.jpa.repository;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;
import lombok.NonNull;
import perobobbot.api.data.ApplicationForPlatformMissing;
import perobobbot.api.data.Platform;
import perobobbot.service.jpa.domain.ApplicationEntity;

import java.util.Optional;
import java.util.stream.Stream;

@Repository
public interface ApplicationRepository extends JpaRepository<ApplicationEntity,Long> {

    @NonNull Optional<String> findClientIdByPlatform(@NonNull Platform platform);

    @NonNull Optional<ApplicationEntity> findByPlatform(@NonNull Platform platform);

    default @NonNull ApplicationEntity getByPlatform(@NonNull Platform platform) {
        return findByPlatform(platform).orElseThrow(() -> new ApplicationForPlatformMissing(platform));
    }

    default @NonNull String getClientIdByPlatform(@NonNull Platform platform) {
        return findClientIdByPlatform(platform).orElseThrow(() -> new ApplicationForPlatformMissing(platform));
    }

    @NonNull Stream<Platform> findPlatform();


}
