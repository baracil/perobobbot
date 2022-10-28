package perobobbot.api;

import com.google.common.collect.ImmutableMap;
import lombok.NonNull;
import lombok.Value;
import perobobbot.api.data.Platform;

@Value
public class Subscription {

    @NonNull Platform platform;
    @NonNull String type;
    @NonNull ImmutableMap<String,String> data;
}
