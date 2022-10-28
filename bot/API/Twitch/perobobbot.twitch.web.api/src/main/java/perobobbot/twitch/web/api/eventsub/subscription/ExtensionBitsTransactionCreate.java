package perobobbot.twitch.web.api.eventsub.subscription;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;
import perobobbot.twitch.web.api.CriteriaType;
import perobobbot.twitch.web.api.SubscriptionType;

@Value
@EqualsAndHashCode(callSuper = true)
public class ExtensionBitsTransactionCreate extends SingleConditionSubscription {

    public static final SubscriptionFactory FACTORY = forSingleCondition(CriteriaType.EXTENSION_CLIENT_ID, ExtensionBitsTransactionCreate::new);


    @NonNull String extensionClientId;

    public ExtensionBitsTransactionCreate(@NonNull String extensionClientId) {
        super(SubscriptionType.EXTENSION_BITS_TRANSACTION_CREATE, CriteriaType.EXTENSION_CLIENT_ID);
        this.extensionClientId = extensionClientId;
    }

    @Override
    protected String getConditionValue() {
        return extensionClientId;
    }
}
