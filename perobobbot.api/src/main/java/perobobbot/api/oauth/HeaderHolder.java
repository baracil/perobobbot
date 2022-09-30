package perobobbot.api.oauth;

import com.google.common.collect.ImmutableMap;
import io.micronaut.core.type.MutableHeaders;
import lombok.NonNull;

public interface HeaderHolder extends DataHolder<ImmutableMap<String,String>> {

    void setHeaders(@NonNull MutableHeaders headers);


}
