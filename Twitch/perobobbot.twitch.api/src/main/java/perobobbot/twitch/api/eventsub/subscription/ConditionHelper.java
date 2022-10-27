package perobobbot.twitch.api.eventsub.subscription;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.twitch.api.Conditions;
import perobobbot.twitch.api.CriteriaType;

import java.util.Optional;

@RequiredArgsConstructor
public class ConditionHelper {

    private final @NonNull Conditions conditions;

    public @NonNull String get(@NonNull CriteriaType criteriaType) {
        return conditions.findConditionValue(criteriaType)
                         .orElseThrow(() -> new NullPointerException("Criteria " + criteriaType + " is required"));

    }

    public @NonNull Optional<String> find(@NonNull CriteriaType criteriaType) {
        return conditions.findConditionValue(criteriaType);
    }

}
