package perobobbot.chat.api.irc;

import jakarta.annotation.Nullable;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.Optional;

@Value
@Builder
@RequiredArgsConstructor
public class Tag {
    boolean client;
    @Nullable String vendor;
    String keyName;
    String value;

    public Optional<String> getVendor() {
        return Optional.ofNullable(vendor);
    }

    public static Tag fromFpc(fpc.tools.irc.Tag tag) {
        return Tag.builder()
                  .client(tag.isClient())
                  .vendor(tag.vendor().orElse(null))
                  .keyName(tag.getKeyName())
                  .value(tag.getValue())
                  .build();
    }
}
