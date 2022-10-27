package perobobbot.twitch.api.eventsub;

import fpc.tools.lang.IdentifiedEnum;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum PollStatus implements IdentifiedEnum {
    COMPLETED("completed"),
    ARCHIVED("archived"),
    TERMINATED("terminated"),
    ;

    @Getter
    private final @NonNull String identification;
}
