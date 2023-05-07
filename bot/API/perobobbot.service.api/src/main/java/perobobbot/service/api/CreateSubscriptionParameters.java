package perobobbot.service.api;

import io.micronaut.serde.annotation.Serdeable;
import lombok.Value;
import perobobbot.api.data.Platform;

import java.util.Map;

@Value
@Serdeable
public class CreateSubscriptionParameters {

    Platform platform;
    String subscriptionType;
    Map<String,String> conditions;
    boolean enabled;

}
