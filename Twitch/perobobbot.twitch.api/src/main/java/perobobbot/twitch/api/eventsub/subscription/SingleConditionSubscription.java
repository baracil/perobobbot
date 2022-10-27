package perobobbot.twitch.api.eventsub.subscription;

import fpc.tools.fp.Function1;
import lombok.*;
import perobobbot.twitch.api.CriteriaType;
import perobobbot.twitch.api.Conditions;
import perobobbot.twitch.api.SubscriptionType;

@EqualsAndHashCode
@ToString
@RequiredArgsConstructor
public abstract class SingleConditionSubscription extends SubscriptionBase {

    public static SubscriptionFactory forSingleCondition(@NonNull CriteriaType criteriaType, @NonNull Function1<? super String, ? extends Subscription> constructor) {
        return condition -> constructor.apply(new ConditionHelper(condition).get(criteriaType));
    }

    @Getter
    private final @NonNull SubscriptionType type;

    private final @NonNull CriteriaType criteriaType;

    @Override
    public @NonNull Conditions getCondition() {
        return Conditions.singleCondition(criteriaType,getConditionValue());
    }

    protected abstract String getConditionValue();
}
