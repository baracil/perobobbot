package perobobbot.api.data;

import io.micronaut.core.annotation.Introspected;

@Introspected
public record JoinChannelParameters(boolean readOnly) {
}
