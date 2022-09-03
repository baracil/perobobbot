package perobobbot.data.launcher;

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
@Slf4j
@RequiredArgsConstructor
public class Discovery {

    private final @NonNull ZookeeperOperations zookeeperOperations;
    private UUID instanceId = null;


    @PostConstruct
    public void registerService() {
        LOG.info("Register application to Zookeeper");
        instanceId = zookeeperOperations.addService(ServicePayload.defaultPayload());
    }

    @PreDestroy
    public void unregisterService() {
        if (instanceId == null) {
            return;
        }
        LOG.info("Unregister application from Zookeeper");
        zookeeperOperations.removeService(instanceId);
    }


}
