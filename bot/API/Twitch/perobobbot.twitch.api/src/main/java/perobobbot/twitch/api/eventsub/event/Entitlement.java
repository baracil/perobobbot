package perobobbot.twitch.api.eventsub.event;

import io.micronaut.serde.annotation.Serdeable;
import io.micronaut.serde.config.naming.SnakeCaseStrategy;
import lombok.NonNull;
import lombok.Value;
import perobobbot.twitch.api.UserInfo;
import perobobbot.twitch.api.serde.ISOInstantSerde;

import java.time.Instant;

@Value
@Serdeable(naming = SnakeCaseStrategy.class)
public class Entitlement  {
    @NonNull String entitlementId;
    @NonNull String benefitId;
    @NonNull String campaignId;
    @NonNull String organizationId;
    @NonNull String categoryId;
    @NonNull String categoryName;
    @NonNull UserInfo user;

    @Serdeable.Serializable(using = ISOInstantSerde.class)
    @Serdeable.Deserializable(using = ISOInstantSerde.class)
    @NonNull Instant createdAt;
}
