package perobobbot.twitch.api;

import fpc.tools.lang.IdentifiedEnum;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum GoalType implements IdentifiedEnum {
    FOLLOW("follow"),
    SUBSCRIPTION("subscription"),
    SUBSCRIPTION_COUNT("subscription_count"),
    NEW_SUBSCRIPTION("new_subscription"),
    NEW_SUBSCRIPTION_COUNT("new_subscription_count")
    ;

    @Getter
    private final @NonNull String identification;
}
