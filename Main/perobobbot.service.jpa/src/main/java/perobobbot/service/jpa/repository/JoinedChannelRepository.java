package perobobbot.service.jpa.repository;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;
import lombok.NonNull;
import perobobbot.api.data.JoinedChannelNotFound;
import perobobbot.service.jpa.domain.JoinedChannelEntity;

import java.util.Optional;
import java.util.stream.Stream;

@Repository
public interface JoinedChannelRepository extends JpaRepository<JoinedChannelEntity,Long> {

    @NonNull Stream<JoinedChannelEntity> getByUserIdentityId(long userIdentityId);

    @NonNull Optional<JoinedChannelEntity> findByUserIdentityIdAndName(long userIdentityId, @NonNull String name);

    default @NonNull JoinedChannelEntity getByUserIdentityIdAndName(long userIdentityId, @NonNull String name) {
        return findByUserIdentityIdAndName(userIdentityId,name).orElseThrow(() -> new JoinedChannelNotFound(userIdentityId,name));
    }
}
