package perobobbot.service.api;

import com.google.common.collect.ImmutableMap;
import io.micronaut.serde.annotation.Serdeable;
import lombok.NonNull;
import lombok.Value;
import perobobbot.api.data.Platform;

@Value
@Serdeable
public class CreateSubscriptionParameters {

    @NonNull Platform platform;
    @NonNull String subscriptionType;
    @NonNull ImmutableMap<String,String> conditions;

}
