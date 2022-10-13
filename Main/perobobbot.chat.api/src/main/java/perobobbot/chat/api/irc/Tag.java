package perobobbot.chat.api.irc;

import io.micronaut.serde.annotation.Serdeable;
import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.Optional;

@Value
@Serdeable
@Builder
@RequiredArgsConstructor
public class Tag {
    boolean client;
    String vendor;
    @NonNull String keyName;
    @NonNull String value;

    public @NonNull Optional<String> getVendor() {
        return Optional.ofNullable(vendor);
    }

    public static @NonNull Tag fromFpc(@NonNull fpc.tools.irc.Tag tag) {
        return Tag.builder()
                  .client(tag.isClient())
                  .vendor(tag.vendor().orElse(null))
                  .keyName(tag.getKeyName())
                  .value(tag.getValue())
                  .build();
    }
}
