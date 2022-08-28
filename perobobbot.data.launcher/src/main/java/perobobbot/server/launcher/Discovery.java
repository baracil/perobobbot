package perobobbot.server.launcher;

import jakarta.inject.Singleton;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import perobobbot.discovery.zookeeper.api.ServiceDetails;
import perobobbot.discovery.zookeeper.api.ServicePayload;
import perobobbot.discovery.zookeeper.api.ServicePort;
import perobobbot.discovery.zookeeper.api.ZookeeperOperations;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.UUID;

@EagerInit
@Singleton
@Slf4j
public class Discovery {

    private final @NonNull ZookeeperOperations zookeeperOperations;
    private final @NonNull ServiceDetails serviceDetails;

    public Discovery(@NonNull ZookeeperOperations zookeeperOperations) {
        this.zookeeperOperations = zookeeperOperations;
        this.serviceDetails = new ServiceDetails(UUID.randomUUID().toString(),"perobobbot-data","localhost", ServicePort.builder().port(8080).build(), ServicePayload.defaultPayload());
    }

    @PostConstruct
    public void registerService() {
        LOG.info("Register application to Zookeeper "+serviceDetails);
        zookeeperOperations.addService(serviceDetails);
    }

    @PreDestroy
    public void unregisterService() {
        LOG.info("Unregister application from Zookeeper "+serviceDetails);
        zookeeperOperations.removeService(serviceDetails);
    }


}
