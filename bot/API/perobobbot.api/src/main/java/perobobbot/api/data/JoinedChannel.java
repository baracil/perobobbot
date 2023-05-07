package perobobbot.api.data;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record JoinedChannel(long id, long userId, String channelName, boolean readOnly) {

}
