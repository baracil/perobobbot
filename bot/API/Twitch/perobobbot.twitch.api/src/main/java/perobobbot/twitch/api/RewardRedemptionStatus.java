package perobobbot.twitch.api;

import fpc.tools.lang.IdentifiedEnumWithAlternateIdentifications;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@RequiredArgsConstructor
public enum RewardRedemptionStatus implements IdentifiedEnumWithAlternateIdentifications {
    UNKNOWN("unknown","UNKNOWN"),
    UNFULFILLED("unfulfilled","UNFULFILLED"),
    FULFILLED("fulfilled","FULFILLED"),
    CANCELED("canceled","CANCELED")
    ;
    @Getter
    private final @NonNull String identification;

    @Getter
    private final @NonNull Set<String> alternateIdentifications;

    RewardRedemptionStatus(@NonNull String identification, @NonNull String alternateIdentifications) {
        this.identification = identification;
        this.alternateIdentifications = Set.of(alternateIdentifications);
    }

    @Override
    public boolean useNameForSerialization() {
        return true;
    }
}
