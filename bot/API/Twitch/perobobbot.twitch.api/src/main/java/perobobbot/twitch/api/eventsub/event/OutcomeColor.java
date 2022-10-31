package perobobbot.twitch.api.eventsub.event;

import fpc.tools.lang.IdentifiedEnum;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum OutcomeColor implements IdentifiedEnum {
    PINK("pink"),
    BLUE("blue"),
    ;
    @Getter
    private final @NonNull String identification;
}
