package perobobbot.twitch.api.eventsub;

import fpc.tools.lang.IdentifiedEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Tier implements IdentifiedEnum {
    TIER_1("1000"),
    TIER_2("2000"),
    TIER_3("3000"),
    ;

    @Getter
    private final String identification;
}
