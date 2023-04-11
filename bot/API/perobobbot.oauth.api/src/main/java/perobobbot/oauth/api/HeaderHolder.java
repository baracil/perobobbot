package perobobbot.oauth.api;

import fpc.tools.lang.ValueHolder;
import io.micronaut.core.type.MutableHeaders;
import lombok.NonNull;

public interface HeaderHolder extends ValueHolder<Header> {

    default void setHeaders(@NonNull MutableHeaders headers) {
        get().ifPresent(m -> m.forEach(headers::add));
    }

    default @NonNull Header.Builder withPrevious() {
        return get().map(Header::toBuilder).orElseGet(Header::builder);
    }

}
