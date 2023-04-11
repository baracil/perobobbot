package perobobbot.service.api;

import io.micronaut.serde.annotation.Serdeable;
import lombok.NonNull;
import lombok.Value;
import perobobbot.api.data.Platform;

import java.util.Map;

@Value
@Serdeable
public class CreateSubscriptionParameters {

    @NonNull Platform platform;
    @NonNull String subscriptionType;
    @NonNull Map<String,String> conditions;
    boolean enabled;

}
