package perobobbot.twitch.web.api.eventsub.subscription;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.twitch.web.api.Conditions;
import perobobbot.twitch.web.api.CriteriaType;
import perobobbot.twitch.web.api.SubscriptionType;
import perobobbot.twitch.web.api.eventsub.TwitchSubscriptionRequest;

@RequiredArgsConstructor
public class DropEntitlementGrant extends SubscriptionBase {

    public static final SubscriptionFactory FACTORY = conditions -> {
        final var helper = new ConditionHelper(conditions);
        return new DropEntitlementGrant(
                helper.get(CriteriaType.ORGANIZATION_ID),
                helper.find(CriteriaType.CATEGORY_ID).orElse(null),
                helper.find(CriteriaType.CAMPAIGN_ID).orElse(null)
        );
    };

    private final @NonNull String organizationId;
    private final String categoryId;
    private final String campaignId;

    @Override
    public @NonNull SubscriptionType getType() {
        return SubscriptionType.DROP_ENTITLEMENT_GRANT;
    }

    @Override
    public @NonNull Conditions getCondition() {
        return Conditions.builder()
                         .put(CriteriaType.ORGANIZATION_ID, organizationId)
                         .put(CriteriaType.CATEGORY_ID, categoryId)
                         .put(CriteriaType.CAMPAIGN_ID, campaignId)
                         .build();
    }

    @Override
    public TwitchSubscriptionRequest.@NonNull Builder completeRequest(TwitchSubscriptionRequest.@NonNull Builder builder) {
        return super.completeRequest(builder).batchingEnabled(true);
    }
}
