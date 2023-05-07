package perobobbot.domain.jpa.repository;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import perobobbot.api.SubscriptionNotFound;
import perobobbot.api.data.Platform;
import perobobbot.domain.jpa.entity.SubscriptionEntity;

@Repository
public interface SubscriptionRepository extends JpaRepository<SubscriptionEntity,Long> {

    Page<SubscriptionEntity> findByPlatform(Platform platform, Pageable pageable);

    default SubscriptionEntity getById(long id) {
        return findById(id).orElseThrow(() -> new SubscriptionNotFound(id));
    }
}
