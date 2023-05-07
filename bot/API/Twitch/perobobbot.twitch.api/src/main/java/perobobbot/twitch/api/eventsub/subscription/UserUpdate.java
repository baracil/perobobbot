package perobobbot.twitch.api.eventsub.subscription;

import lombok.EqualsAndHashCode;
import lombok.Value;
import perobobbot.twitch.api.CriteriaType;
import perobobbot.twitch.api.SubscriptionType;

@Value
@EqualsAndHashCode(callSuper = true)
public class UserUpdate extends SingleConditionSubscription {

    public static final SubscriptionFactory FACTORY = forSingleCondition(CriteriaType.USER_ID, UserUpdate::new);

    String userId;

    public UserUpdate(String userId) {
        super(SubscriptionType.USER_UPDATE,CriteriaType.USER_ID);
        this.userId = userId;
    }

    @Override
    protected String getConditionValue() {
        return userId;
    }
}
