package perobobbot.twitch.api.eventsub.subscription;

import perobobbot.api.SubscriptionView;

import java.util.UUID;

public record UserSubscriptionView(UUID id,
                                   String login,
                                   SubscriptionView subscription) {


}
