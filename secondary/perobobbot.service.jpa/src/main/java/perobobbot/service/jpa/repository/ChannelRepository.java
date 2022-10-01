package perobobbot.service.jpa.repository;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;
import perobobbot.service.jpa.domain.ChannelEntity;

@Repository
public interface ChannelRepository extends JpaRepository<ChannelEntity,Long> {


}
