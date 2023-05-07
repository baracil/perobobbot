package perobobbot.twitch.api.eventsub.event;

import fpc.tools.lang.IdentifiedEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum PredicationStatus implements IdentifiedEnum {
    RESOLVED("resolved"),
    CANCELED("canceled")
    ;
    @Getter
    private final String identification;
}
