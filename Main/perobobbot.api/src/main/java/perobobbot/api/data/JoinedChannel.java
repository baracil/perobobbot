package perobobbot.api.data;

import io.micronaut.serde.annotation.Serdeable;
import lombok.NonNull;

@Serdeable
public record JoinedChannel(long id, long userId, @NonNull String channelName, boolean readOnly) {

}
