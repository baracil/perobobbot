package perobobbot.api;

import com.google.common.collect.ImmutableMap;
import lombok.NonNull;

public interface SerDeHelper {

    @NonNull String serializeMap(@NonNull ImmutableMap<String,String> map);
    @NonNull ImmutableMap<String,String> deserializeMap(@NonNull String data);
}
