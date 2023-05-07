package perobobbot.domain.jpa.entity;

import lombok.*;
import perobobbot.api.Identity;
import perobobbot.api.UserInfo;
import perobobbot.api.data.Platform;
import perobobbot.api.data.UserIdentity;
import perobobbot.api.data.UserToken;
import perobobbot.api.data.UserType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "USER_IDENTITY", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"PLATFORM", "USER_ID"}, name = "uk_platform__user_id"),
})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Getter
@Setter(AccessLevel.PROTECTED)
@EqualsAndHashCode(of = {"platform", "userId"}, callSuper = false)
public class UserIdentityEntity extends BaseEntity {

    @Column(name = "PLATFORM", nullable = false)
    @Convert(converter = PlatformConverter.class)
    private Platform platform;

    @Column(name = "USER_ID", nullable = false)
    private String userId;

    @Column(name = "LOGIN", nullable = false)
    private String login;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "USER_TYPE", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserType userType;

    @OneToOne(mappedBy = "userIdentity", cascade = CascadeType.ALL, orphanRemoval = true)
    private UserTokenEntity tokenEntity;

    @OneToMany(mappedBy = "userIdentity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JoinedChannelEntity> joinedChannels = new ArrayList<>();


    public static UserIdentityEntity createWith(UserInfo userInfo, UserType userType) {
        return new UserIdentityEntity(
                userInfo.platform(),
                userInfo.userId(),
                userInfo.login(),
                userInfo.name(),
                userType,
                null,
                new ArrayList<>());
    }

    public UserIdentity toView() {
        return new UserIdentity(
                getId(),
                createIdentification(),
                userType,
                login,
                name,
                joinedChannels.stream().map(JoinedChannelEntity::toView).toList()
        );
    }

    public Identity createIdentification() {
        return new Identity(platform, userId);
    }

    public UserIdentityEntity updateToken(UserToken.Encrypted userToken) {
        createIdentification().checkSameIdentity(userToken.identity());
        this.tokenEntity = UserTokenEntity.createWith(this, userToken);
        return this;
    }


    public boolean isSameIdentity(Identity identity) {
        return identity.isSameIdentity(platform, userId);
    }

    public JoinedChannelEntity joinedChannel(String channelName, boolean readOnly) {
        final var result = new JoinedChannelEntity(this, channelName, readOnly);
        this.joinedChannels.add(result);
        return result;
    }

    public boolean partChannel(String channelName) {
        return this.joinedChannels.removeIf(jc -> jc.getName().equals(channelName));
    }

    public UserInfo toUserInfo() {
        return new UserInfo(createIdentification(), login, name);
    }

    public void update(String login, String name) {
        this.login = login;
        this.name = name;
    }
}
