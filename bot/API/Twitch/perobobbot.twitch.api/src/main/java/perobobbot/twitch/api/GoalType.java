package perobobbot.twitch.api;

import com.google.common.collect.ImmutableSet;
import fpc.tools.lang.IdentifiedEnumWithAlternateIdentifications;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum GoalType implements IdentifiedEnumWithAlternateIdentifications {
    FOLLOW("follow","follower"),
    SUBSCRIPTION("subscription"),
    SUBSCRIPTION_COUNT("subscription_count"),
    NEW_SUBSCRIPTION("new_subscription"),
    NEW_SUBSCRIPTION_COUNT("new_subscription_count")
    ;

    @Getter
    private final @NonNull String identification;
    @Getter
    private final @NonNull ImmutableSet<String> alternateIdentifications;

    GoalType(@NonNull String identification, String... alternateIdentifications) {
        this.identification = identification;
        this.alternateIdentifications = alternateIdentifications == null?ImmutableSet.of():ImmutableSet.copyOf(alternateIdentifications);
    }
}
