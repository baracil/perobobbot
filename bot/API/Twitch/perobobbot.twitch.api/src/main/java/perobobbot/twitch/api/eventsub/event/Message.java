package perobobbot.twitch.api.eventsub.event;

import io.micronaut.serde.annotation.Serdeable;
import lombok.Value;

import java.util.List;

@Value
@Serdeable
public class Message {

    String text;
    List<Emote> emotes;
}
