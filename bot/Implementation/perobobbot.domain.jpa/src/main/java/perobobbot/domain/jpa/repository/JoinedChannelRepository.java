package perobobbot.domain.jpa.repository;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;
import perobobbot.api.data.JoinedChannelNotFound;
import perobobbot.domain.jpa.entity.JoinedChannelEntity;

import java.util.Optional;
import java.util.stream.Stream;

@Repository
public interface JoinedChannelRepository extends JpaRepository<JoinedChannelEntity,Long> {

    Stream<JoinedChannelEntity> getByUserIdentityId(long userIdentityId);

    Optional<JoinedChannelEntity> findByUserIdentityIdAndName(long userIdentityId, String name);

    default JoinedChannelEntity getByUserIdentityIdAndName(long userIdentityId, String name) {
        return findByUserIdentityIdAndName(userIdentityId,name).orElseThrow(() -> new JoinedChannelNotFound(userIdentityId,name));
    }
}
