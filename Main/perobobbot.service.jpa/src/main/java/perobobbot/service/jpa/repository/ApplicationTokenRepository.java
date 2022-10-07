package perobobbot.service.jpa.repository;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;
import lombok.NonNull;
import perobobbot.api.data.Platform;
import perobobbot.service.jpa.domain.ApplicationTokenEntity;

import java.util.Optional;

@Repository
public interface ApplicationTokenRepository extends JpaRepository<ApplicationTokenEntity,Long> {

    @NonNull Optional<ApplicationTokenEntity> findByApplicationPlatform(@NonNull Platform platform);

}
