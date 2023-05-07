package perobobbot.domain.jpa.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
    private Platform platform;

    private String type;

    private String subscriptionId;

    private String data;

    private String callback;

    @Setter
    private boolean enabled;

    public SubscriptionEntity(Platform platform,
                              String type,
                              String data,
                              String callback,
                              boolean enabled) {
        this.platform = platform;
        this.type = type;
        this.subscriptionId = "";
        this.data = data;
        this.callback = callback;
        this.enabled = enabled;
    }

    public SubscriptionView toView(SerDeHelper serDeHelper) {
        return new SubscriptionView(getId(), platform, type, serDeHelper.deserializeMap(data), subscriptionId, enabled, callback);
    }

    public void update(String subscriptionId, String callback) {
        this.subscriptionId = subscriptionId;
        this.callback = callback;
    }
}
