package perobobbot.api;

import lombok.NonNull;

import java.util.Map;

public interface SerDeHelper {

    @NonNull String serializeMap(@NonNull Map<String,String> map);
    @NonNull Map<String,String> deserializeMap(@NonNull String data);
}
