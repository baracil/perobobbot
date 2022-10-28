package perobobbot.twitch.web.api.eventsub.event;

import lombok.NonNull;
import lombok.Value;
import perobobbot.twitch.api.TwitchApiPayload;
import perobobbot.twitch.web.api.UserInfo;

import java.time.Instant;

@Value
public class Entitlement implements TwitchApiPayload {
    @NonNull String organizationId;
    @NonNull String categoryId;
    @NonNull String categoryName;
    @NonNull String campaignId;
    @NonNull UserInfo user;
    @NonNull String entitlementId;
    @NonNull String benefitId;
    @NonNull Instant createdAt;
}
