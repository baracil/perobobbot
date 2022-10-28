package perobobbot.twitch.web.api.eventsub.event;

import fpc.tools.lang.IdentifiedEnum;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum PredicationStatus implements IdentifiedEnum {
    RESOLVED("resolved"),
    CANCELED("canceled")
    ;
    @Getter
    private final @NonNull String identification;
}
