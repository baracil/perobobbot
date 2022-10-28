package perobobbot.domain.jpa.entity;

import lombok.*;
import org.hibernate.annotations.Type;
import perobobbot.api.SerDeHelper;
import perobobbot.api.Subscription;
import perobobbot.api.data.Platform;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@NoArgsConstructor(access= AccessLevel.PROTECTED)
@Getter
@Setter(AccessLevel.PROTECTED)
@Table(name = "SUBSCRIPTION")
public class SubscriptionEntity extends BaseEntity {

    @Type(type = "perobobbot.domain.jpa.entity.PlatformType")
    private @NonNull Platform platform;

    private @NonNull String type;

    private @NonNull String data;

    public SubscriptionEntity(@NonNull Platform platform,
                              @NonNull String type,
                              @NonNull String data) {
        this.platform = platform;
        this.type = type;
        this.data = data;
    }

    public @NonNull Subscription toView(@NonNull SerDeHelper serDeHelper) {
        return new Subscription(platform,type,serDeHelper.deser(data));
    }
}
