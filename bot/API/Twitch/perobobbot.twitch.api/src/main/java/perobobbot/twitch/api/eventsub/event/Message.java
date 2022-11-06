package perobobbot.twitch.api.eventsub.event;

import com.google.common.collect.ImmutableList;
import io.micronaut.serde.annotation.Serdeable;
import lombok.NonNull;
import lombok.Value;

@Value
@Serdeable
public class Message {

    @NonNull String text;
    @NonNull ImmutableList<Emote> emotes;
}
