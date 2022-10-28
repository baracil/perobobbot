package perobobbot.api;

import com.google.common.collect.ImmutableMap;
import lombok.NonNull;

public interface SerDeHelper {

    @NonNull ImmutableMap<String,String> deser(@NonNull String data);
}
