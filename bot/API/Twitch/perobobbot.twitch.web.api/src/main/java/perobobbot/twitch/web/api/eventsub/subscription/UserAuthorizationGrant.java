package perobobbot.twitch.web.api.eventsub.subscription;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;
import perobobbot.twitch.web.api.CriteriaType;
import perobobbot.twitch.web.api.SubscriptionType;

@Value
@EqualsAndHashCode(callSuper = true)
public class UserAuthorizationGrant extends SingleConditionSubscription {

    public static final SubscriptionFactory FACTORY = forSingleCondition(CriteriaType.CLIENT_ID, UserAuthorizationGrant::new);

    @NonNull String clientId;

    public UserAuthorizationGrant(@NonNull String clientId) {
        super(SubscriptionType.USER_AUTHORIZATION_GRANT, CriteriaType.CLIENT_ID);
        this.clientId = clientId;
    }

    @Override
    protected String getConditionValue() {
        return clientId;
    }
}
