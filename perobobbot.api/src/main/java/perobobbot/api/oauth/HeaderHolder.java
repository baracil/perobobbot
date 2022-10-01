package perobobbot.api.oauth;

import com.google.common.collect.ImmutableMap;
import fpc.tools.lang.ValueHolder;
import io.micronaut.core.type.MutableHeaders;
import lombok.NonNull;

public interface HeaderHolder extends ValueHolder<ImmutableMap<String, String>> {

    default void setHeaders(@NonNull MutableHeaders headers) {
        get().ifPresent(m -> m.forEach(headers::add));
    }

    default @NonNull ImmutableMap.Builder<String,String> withPrevious() {
        final var builder = ImmutableMap.<String,String>builder();
        get().ifPresent(builder::putAll);
        return builder;
    }

}
