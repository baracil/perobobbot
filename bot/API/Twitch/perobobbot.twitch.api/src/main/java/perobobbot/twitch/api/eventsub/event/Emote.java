package perobobbot.twitch.api.eventsub.event;

import io.micronaut.core.annotation.Nullable;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Value;

@Value
@Serdeable
public class Emote {
    int begin;
    int end;
    @Nullable String id;
}
