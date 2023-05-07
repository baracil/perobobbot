package perobobbot.twitch.api.eventsub.event;

import fpc.tools.lang.IdentifiedEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ContributionType implements IdentifiedEnum {
    BITS("bits"),
    SUBSCRIPTION("subscription")
    ;
    @Getter
    private final String identification;
}
