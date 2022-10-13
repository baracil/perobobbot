package perobobbot.chat.api;

import io.micronaut.serde.annotation.Serdeable;
import lombok.NonNull;
import perobobbot.api.Identification;

@Serdeable
public record ChatMessage(@NonNull Identification identification,
                          @NonNull Object payload) {

}
