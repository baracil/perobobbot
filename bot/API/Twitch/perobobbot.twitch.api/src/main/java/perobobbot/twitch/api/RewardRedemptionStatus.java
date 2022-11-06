package perobobbot.twitch.api;

import com.google.common.collect.ImmutableSet;
import fpc.tools.lang.IdentifiedEnumWithAlternateIdentifications;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

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
    private final @NonNull ImmutableSet<String> alternateIdentifications;

    RewardRedemptionStatus(@NonNull String identification, @NonNull String alternateIdentifications) {
        this.identification = identification;
        this.alternateIdentifications = ImmutableSet.of(alternateIdentifications);
    }

    @Override
    public boolean useNameForSerialization() {
        return true;
    }
}
