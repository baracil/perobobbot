package perobobbot.twitch.api.eventsub.event;

import lombok.Value;

@Value
public class Emote {
    int begin;
    int end;
    String id;
}
