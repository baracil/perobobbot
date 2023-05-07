package perobobbot.twitch.api;

import fpc.tools.lang.IdentifiedEnumWithAlternateIdentifications;
import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@RequiredArgsConstructor
public enum GoalType implements IdentifiedEnumWithAlternateIdentifications {
    FOLLOW("follow","follower"),
    SUBSCRIPTION("subscription"),
    SUBSCRIPTION_COUNT("subscription_count"),
    NEW_SUBSCRIPTION("new_subscription"),
    NEW_SUBSCRIPTION_COUNT("new_subscription_count")
    ;

    @Getter
    private final String identification;
    @Getter
    private final Set<String> alternateIdentifications;

    GoalType(String identification, @Nullable String... alternateIdentifications) {
        this.identification = identification;
        this.alternateIdentifications = alternateIdentifications == null?Set.of():Set.of(alternateIdentifications);
    }
}
