package perobobbot.twitch.api;

import fpc.tools.lang.IdentifiedEnumWithAlternateIdentification;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum RewardRedemptionStatus implements IdentifiedEnumWithAlternateIdentification {
    UNKNOWN("unknown","UNKNOWN"),
    UNFULFILLED("unfulfilled","UNFULFILLED"),
    FULFILLED("fulfilled","FULFILLED"),
    CANCELED("canceled","CANCELED")
    ;
    @Getter
    private final @NonNull String identification;
    @Getter
    private final @NonNull String alternateIdentification;
}
