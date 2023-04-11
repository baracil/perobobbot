package perobobbot.twitch.api.eventsub.event;

import io.micronaut.serde.annotation.Serdeable;
import lombok.NonNull;
import lombok.Value;

import java.util.List;

@Value
@Serdeable
public class Message {

    @NonNull String text;
    @NonNull List<Emote> emotes;
}
