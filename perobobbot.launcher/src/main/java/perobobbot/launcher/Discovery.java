package perobobbot.launcher;

import fpc.tools.micronaut.EagerInit;
import jakarta.inject.Singleton;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import perobobbot.discovery.zookeeper.api.ServicePayload;
import perobobbot.discovery.zookeeper.api.ZookeeperOperations;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.UUID;

@Singleton
@EagerInit
@Slf4j
@RequiredArgsConstructor
public class Discovery {

    private final @NonNull ZookeeperOperations zookeeperOperations;
    private final @NonNull String id = UUID.randomUUID().toString();


    @PostConstruct
    public void registerService() {
        LOG.info("Register application to Zookeeper");
        zookeeperOperations.registerService(id, ServicePayload.defaultPayload());
    }

    @PreDestroy
    public void unregisterService() {
        LOG.info("Unregister application from Zookeeper");
        zookeeperOperations.unregisterService(id);
    }


}
