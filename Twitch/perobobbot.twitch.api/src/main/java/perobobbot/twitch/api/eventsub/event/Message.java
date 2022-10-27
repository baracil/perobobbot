package perobobbot.twitch.api.eventsub.event;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.Value;

@Value
public class Message {

    @NonNull String text;
    @NonNull ImmutableList<Emote> emotes;
}
