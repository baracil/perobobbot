package perobobbot.service.jpa.domain;

import lombok.*;
import perobobbot.api.data.Channel;

import javax.persistence.*;

@Entity
@Table(name = "CHANNEL", uniqueConstraints = {@UniqueConstraint(columnNames = {"USER_IDENTITY_ID", "CHANNEL_NAME"}, name = "UK_USER_IDENTITY__CHANNEL_NAME")})
@NoArgsConstructor
@Getter
@Setter(AccessLevel.PROTECTED)
public class ChannelEntity extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "USER_IDENTITY_ID", nullable = false)
    private @NonNull UserIdentityEntity userIdentity;

    @Column(name = "CHANNEL_NAME")
    private @NonNull String channelName;

    @Setter
    private boolean mute;

    ChannelEntity(@NonNull UserIdentityEntity userIdentity, @NonNull String channelName, boolean mute) {
        this.userIdentity = userIdentity;
        this.channelName = channelName;
        this.mute = mute;
    }

    public @NonNull Channel toView() {
        return new Channel(getId(), userIdentity.toView(), channelName, mute);
    }
}
