package perobobbot.domain.jpa.repository;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;
import lombok.NonNull;
import perobobbot.api.SubscriptionNotFound;
import perobobbot.api.data.Platform;
import perobobbot.domain.jpa.entity.SubscriptionEntity;

import java.util.stream.Stream;

@Repository
public interface SubscriptionRepository extends JpaRepository<SubscriptionEntity,Long> {

    @NonNull Stream<SubscriptionEntity> findByPlatform(@NonNull Platform platform);

    default @NonNull SubscriptionEntity getById(long id) {
        return findById(id).orElseThrow(() -> new SubscriptionNotFound(id));
    }
}
