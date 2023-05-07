package perobobbot.twitch.api;

import fpc.tools.lang.IdentifiedEnumWithAlternateIdentifications;
import lombok.Getter;
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
    private final String identification;

    @Getter
    private final Set<String> alternateIdentifications;

    RewardRedemptionStatus(String identification, String alternateIdentifications) {
        this.identification = identification;
        this.alternateIdentifications = Set.of(alternateIdentifications);
    }

    @Override
    public boolean useNameForSerialization() {
        return true;
    }
}
