package perobobbot.service.jpa.domain;

import lombok.*;
import perobobbot.api.data.JoinedChannel;

import javax.persistence.*;

@Entity
@Table(name = "JOINED_CHANNEL",uniqueConstraints = {@UniqueConstraint(columnNames = {"NAME","USER_IDENTITY_ID"},name = "uk_name__user_identity_id")})
@NoArgsConstructor
@Getter
@Setter(AccessLevel.PROTECTED)
public class JoinedChannelEntity extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "USER_IDENTITY_ID",
            foreignKey = @ForeignKey(name = "FK_JOINED_CHANNEL__USER_IDENTITY"),
            nullable = false)
    private @NonNull UserIdentityEntity userIdentity;

    @Column(name = "USER_IDENTITY_ID",nullable = false, insertable = false, updatable = false)
    private long userIdentityId;

    @Column(name = "NAME",nullable = false)
    private @NonNull String name;

    @Column(name = "READ_ONLY",nullable = false)
    @Setter
    private boolean readOnly;

    JoinedChannelEntity(@NonNull UserIdentityEntity userIdentity, @NonNull String name, boolean readOnly) {
        this.userIdentity = userIdentity;
        this.userIdentityId = userIdentity.getId();
        this.name = name;
        this.readOnly = readOnly;
    }

    public JoinedChannel toView() {
        return new JoinedChannel(getId(), userIdentityId, name, readOnly);
    }
}
