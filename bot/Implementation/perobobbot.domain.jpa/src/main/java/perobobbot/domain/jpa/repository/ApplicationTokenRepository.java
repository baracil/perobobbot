package perobobbot.domain.jpa.repository;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;
import perobobbot.api.data.Platform;
import perobobbot.domain.jpa.entity.ApplicationTokenEntity;

import java.util.Optional;

@Repository
public interface ApplicationTokenRepository extends JpaRepository<ApplicationTokenEntity,Long> {

    Optional<ApplicationTokenEntity> findByApplicationPlatform(Platform platform);

}
