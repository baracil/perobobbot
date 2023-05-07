package perobobbot.twitch.api.eventsub.subscription;

import fpc.tools.fp.Function1;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import perobobbot.twitch.api.Conditions;
import perobobbot.twitch.api.CriteriaType;
import perobobbot.twitch.api.SubscriptionType;

@EqualsAndHashCode
@ToString
@RequiredArgsConstructor
public abstract class SingleConditionSubscription extends SubscriptionBase {

    public static SubscriptionFactory forSingleCondition(CriteriaType criteriaType, Function1<? super String, ? extends Subscription> constructor) {
        return condition -> constructor.apply(new ConditionHelper(condition).get(criteriaType));
    }

    @Getter
    private final SubscriptionType type;

    private final CriteriaType criteriaType;

    @Override
    public Conditions getCondition() {
        return Conditions.singleCondition(criteriaType,getConditionValue());
    }

    protected abstract String getConditionValue();
}
