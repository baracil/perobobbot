package perobobbot.twitch.api.eventsub.event;

import fpc.tools.lang.IdentifiedEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum StreamType implements IdentifiedEnum {
    LIVE("live"),
    PLAYLIST("playlist"),
    WATCH_PARTY("watch_party"),
    PREMIERE("premiere"),
    RERUN("rerun")
    ;
    @Getter private final String identification;
}
