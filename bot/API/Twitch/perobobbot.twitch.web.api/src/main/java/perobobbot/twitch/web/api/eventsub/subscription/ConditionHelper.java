package perobobbot.twitch.web.api.eventsub.subscription;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.twitch.web.api.Conditions;
import perobobbot.twitch.web.api.CriteriaType;

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
