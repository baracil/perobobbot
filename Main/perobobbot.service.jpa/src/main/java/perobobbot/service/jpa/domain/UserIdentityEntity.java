package perobobbot.service.jpa.domain;

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
@Table(name = "USER_IDENTITY", uniqueConstraints = {@UniqueConstraint(columnNames = {"PLATFORM", "USER_ID"})})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
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

    @OneToMany(mappedBy = "userIdentity", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<ChannelEntity> channels = new ArrayList<>();

    public static @NonNull UserIdentityEntity createWith(@NonNull UserInfo userInfo, @NonNull UserType userType) {
        return new UserIdentityEntity(userInfo.platform(),
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
                name
        );
    }

    public @NonNull Identification createIdentification() {
        return new Identification(platform, userId);
    }

    public @NonNull UserIdentityEntity updateToken(@NonNull UserToken.Encrypted userToken) {
        createIdentification().checkSamePerson(userToken.identification());
        this.tokenEntity = UserTokenEntity.createWith(this, userToken);
        return this;
    }


    public @NonNull ChannelEntity addChannel(@NonNull String channelName, boolean mute) {
        final var channel = new ChannelEntity(this, channelName, mute);
        this.channels.add(channel);
        return channel;
    }

    public void removeChannel(@NonNull String channelName) {
        this.channels.removeIf(c -> c.getChannelName().equals(channelName));
    }

    public UserInfo toUserInfo() {
        return null;
    }
}
