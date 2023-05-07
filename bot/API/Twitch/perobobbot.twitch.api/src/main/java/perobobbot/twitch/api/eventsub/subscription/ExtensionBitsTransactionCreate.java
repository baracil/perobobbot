package perobobbot.twitch.api.eventsub.subscription;

import lombok.EqualsAndHashCode;
import lombok.Value;
import perobobbot.twitch.api.CriteriaType;
import perobobbot.twitch.api.SubscriptionType;

@Value
@EqualsAndHashCode(callSuper = true)
public class ExtensionBitsTransactionCreate extends SingleConditionSubscription {

    public static final SubscriptionFactory FACTORY = forSingleCondition(CriteriaType.EXTENSION_CLIENT_ID, ExtensionBitsTransactionCreate::new);


    String extensionClientId;

    public ExtensionBitsTransactionCreate(String extensionClientId) {
        super(SubscriptionType.EXTENSION_BITS_TRANSACTION_CREATE, CriteriaType.EXTENSION_CLIENT_ID);
        this.extensionClientId = extensionClientId;
    }

    @Override
    protected String getConditionValue() {
        return extensionClientId;
    }
}
