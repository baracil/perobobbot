package perobobbot.api.data;

import io.micronaut.core.annotation.Introspected;
import lombok.NonNull;

@Introspected
public record JoinedChannel(long id, long userId, @NonNull String channelName, boolean readOnly) {

}
