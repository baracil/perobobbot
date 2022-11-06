package perobobbot.domain.jpa.entity;

import lombok.*;
import org.hibernate.annotations.Type;
import perobobbot.api.SerDeHelper;
import perobobbot.api.SubscriptionView;
import perobobbot.api.data.Platform;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter(AccessLevel.PROTECTED)
@Table(name = "SUBSCRIPTION")
public class SubscriptionEntity extends BaseEntity {

    @Type(type = "perobobbot.domain.jpa.entity.PlatformType")
    private @NonNull Platform platform;

    private @NonNull String type;

    private @NonNull String subscriptionId;

    private @NonNull String data;

    private @NonNull String callback;

    public SubscriptionEntity(@NonNull Platform platform,
                              @NonNull String type,
                              @NonNull String data,
                              @NonNull String callback) {
        this.platform = platform;
        this.type = type;
        this.subscriptionId = "";
        this.data = data;
        this.callback = callback;
    }

    public @NonNull SubscriptionView toView(@NonNull SerDeHelper serDeHelper) {
        return new SubscriptionView(getId(), platform, type, serDeHelper.deserializeMap(data), subscriptionId, callback);
    }

    public void update(@NonNull String subscriptionId, @NonNull String callback) {
        this.subscriptionId = subscriptionId;
        this.callback = callback;
    }
}
