package perobobbot.twitch.api.eventsub.subscription;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;
import perobobbot.twitch.api.CriteriaType;
import perobobbot.twitch.api.SubscriptionType;

@Value
@EqualsAndHashCode(callSuper = true)
public class UserAuthorizationRevoke extends SingleConditionSubscription {

    public static final SubscriptionFactory FACTORY = forSingleCondition(CriteriaType.CLIENT_ID, UserAuthorizationRevoke::new);

    @NonNull String clientId;

    public UserAuthorizationRevoke(@NonNull String clientId) {
        super(SubscriptionType.USER_AUTHORIZATION_REVOKE, CriteriaType.CLIENT_ID);
        this.clientId = clientId;
    }

    @Override
    protected String getConditionValue() {
        return clientId;
    }
}
