package perobobbot.service.jpa.domain;

import com.google.common.collect.ImmutableList;
import lombok.*;
import perobobbot.api.Identification;
import perobobbot.api.UserInfo;
import perobobbot.api.data.Platform;
import perobobbot.api.data.UserType;
import perobobbot.api.data.view.UserIdentity;
import perobobbot.api.data.view.UserToken;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "USER_IDENTITY", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"PLATFORM", "USER_ID"},name = "uk_platform__user_id"),
})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Getter
@Setter(AccessLevel.PROTECTED)
@EqualsAndHashCode(of = {"platform", "userId"}, callSuper = false)
public class UserIdentityEntity extends BaseEntity {

    @Column(name = "PLATFORM", nullable = false)
    @Convert(converter = PlatformConverter.class)
    private @NonNull Platform platform;

    @Column(name = "USER_ID", nullable = false)
    private @NonNull String userId;

    @Column(name = "LOGIN", nullable = false)
    private @NonNull String login;

    @Column(name = "NAME", nullable = false)
    private @NonNull String name;

    @Column(name = "USER_TYPE", nullable = false)
    @Enumerated(EnumType.STRING)
    private @NonNull UserType userType;

    @OneToOne(mappedBy = "userIdentity", cascade = CascadeType.ALL, orphanRemoval = true)
    private UserTokenEntity tokenEntity;

    @OneToMany(mappedBy = "userIdentity", cascade = CascadeType.ALL,orphanRemoval = true)
    private @NonNull List<JoinedChannelEntity> joinedChannels = new ArrayList<>();


    public static @NonNull UserIdentityEntity createWith(@NonNull UserInfo userInfo, @NonNull UserType userType) {
        return new UserIdentityEntity(
                userInfo.platform(),
                userInfo.userId(),
                userInfo.login(),
                userInfo.name(),
                userType,
                null,
                new ArrayList<>());
    }

    public @NonNull UserIdentity toView() {
        return new UserIdentity(
                getId(),
                createIdentification(),
                userType,
                login,
                name,
                joinedChannels.stream().map(JoinedChannelEntity::toView).collect(ImmutableList.toImmutableList())
        );
    }

    public @NonNull Identification createIdentification() {
        return new Identification(platform, userId);
    }

    public @NonNull UserIdentityEntity updateToken(@NonNull UserToken.Encrypted userToken) {
        createIdentification().checkSameIdentity(userToken.identification());
        this.tokenEntity = UserTokenEntity.createWith(this, userToken);
        return this;
    }


    public boolean isSameIdentity(@NonNull Identification identification) {
        return identification.isSameIdentity(platform, userId);
    }

    public @NonNull JoinedChannelEntity joinedChannel(@NonNull String channelName, boolean readOnly) {
        final var result = new JoinedChannelEntity(this,channelName,readOnly);
        this.joinedChannels.add(result);
        return result;
    }

    public boolean partChannel(@NonNull String channelName) {
        return this.joinedChannels.removeIf(jc -> jc.getName().equals(channelName));
    }
}
