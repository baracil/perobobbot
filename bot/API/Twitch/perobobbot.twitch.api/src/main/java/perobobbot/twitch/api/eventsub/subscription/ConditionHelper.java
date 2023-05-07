package perobobbot.twitch.api.eventsub.subscription;

import lombok.RequiredArgsConstructor;
import perobobbot.twitch.api.Conditions;
import perobobbot.twitch.api.CriteriaType;

import java.util.Optional;

@RequiredArgsConstructor
public class ConditionHelper {

    private final Conditions conditions;

    public String get(CriteriaType criteriaType) {
        return conditions.findConditionValue(criteriaType)
                         .orElseThrow(() -> new NullPointerException("Criteria " + criteriaType + " is required"));

    }

    public Optional<String> find(CriteriaType criteriaType) {
        return conditions.findConditionValue(criteriaType);
    }

}
