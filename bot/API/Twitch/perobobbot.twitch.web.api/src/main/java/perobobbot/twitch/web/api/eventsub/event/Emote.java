package perobobbot.twitch.web.api.eventsub.event;

import lombok.Value;

@Value
public class Emote {
    int begin;
    int end;
    String id;
}
