package perobobbot.api.data;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record JoinChannelParameters(boolean readOnly) {
}
