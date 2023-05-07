package perobobbot.oauth.api;

import fpc.tools.lang.ValueHolder;
import io.micronaut.core.type.MutableHeaders;

public interface HeaderHolder extends ValueHolder<Header> {

    default void setHeaders(MutableHeaders headers) {
        get().ifPresent(m -> m.forEach(headers::add));
    }

    default Header.Builder withPrevious() {
        return get().map(Header::toBuilder).orElseGet(Header::builder);
    }

}
