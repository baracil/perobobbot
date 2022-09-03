package perobobbot.data.service.jpa.repository;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;
import lombok.NonNull;
import perobobbot.data.service.jpa.domain.ApplicationToken;

import java.util.Optional;

@Repository
public interface ApplicationTokenRepository extends JpaRepository<ApplicationToken,Long> {

    @NonNull Optional<ApplicationToken> findByApplicationPlatform(@NonNull String platform);

}
