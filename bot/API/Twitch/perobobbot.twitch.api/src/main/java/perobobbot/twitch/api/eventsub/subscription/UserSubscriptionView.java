package perobobbot.twitch.api.eventsub.subscription;

import lombok.NonNull;
import perobobbot.api.SubscriptionView;

import java.util.UUID;

public record UserSubscriptionView(@NonNull UUID id,
                                   @NonNull String login,
                                   @NonNull SubscriptionView subscription) {


}
