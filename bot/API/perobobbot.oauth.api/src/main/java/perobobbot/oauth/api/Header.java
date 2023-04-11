package perobobbot.oauth.api;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Singular;

import java.util.Map;
import java.util.function.BiConsumer;

@RequiredArgsConstructor
@Builder(builderClassName = "Builder",toBuilder = true)
public class Header {

    @Singular
    private final Map<String,String> headers;


    public void forEach(BiConsumer<? super String, ? super String> action) {
        headers.forEach(action);
    }
}
