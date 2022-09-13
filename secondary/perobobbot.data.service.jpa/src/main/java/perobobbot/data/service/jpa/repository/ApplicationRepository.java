package perobobbot.data.service.jpa.repository;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;
import lombok.NonNull;
import perobobbot.data.io.ApplicationForPlatformMissing;
import perobobbot.data.io.Platform;
import perobobbot.data.service.jpa.domain.Application;

import java.util.Optional;

@Repository
public interface ApplicationRepository extends JpaRepository<Application,Long> {

    @NonNull Optional<Application> findByPlatform(@NonNull Platform platform);

    default @NonNull Application getByPlatform(@NonNull Platform platform) {
        return findByPlatform(platform).orElseThrow(() -> new ApplicationForPlatformMissing(platform));
    }

}
