package perobobbot.service.api;

import perobobbot.api.Id;
import perobobbot.api.Identity;
import perobobbot.api.JoinedChannelProvider;
import perobobbot.api.UserInfo;
import perobobbot.api.data.JoinedChannel;
import perobobbot.api.data.Platform;
import perobobbot.api.data.UnknownUserId;
import perobobbot.api.data.UserIdentity;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface UserIdentityService extends JoinedChannelProvider {

    int VERSION = 1;

    /**
     * @param userIdentityId the id of the user identity
     * @return the {@link UserIdentity} associated with the provided id
     * @throws perobobbot.api.data.UnknownUserIdentityId if no userIdentity exists for the provided id
     */
    UserIdentity getUserIdentity(long userIdentityId);

    Optional<UserInfo> findUserInfo(Identity identity);

    default UserIdentity getUserIdentity(Id id) {
        return findUserIdentity(id).orElseThrow(() -> new UnknownUserId(id));
    }

    Optional<UserIdentity> findUserIdentity(Id id);

    /**
     * List all the user identities
     *
     * @param page the page number
     * @param size the size of the page (-1 for no size)
     * @return a list of the {@link UserIdentity} for the provided page information
     */
    List<UserIdentity> listUserIdentities(int page, int size);

    List<UserIdentity> listUserIdentities(Platform platform, int page, int size);

    JoinedChannel joinChannel(long userIdentityId, String channelName, boolean readOnly);

    void partChannel(long userIdentityId, String channelName);

    Set<JoinedChannel> listJoinedChannels(long userIdentityId);

    JoinedChannel getJoinedChannel(long userIdentityId, String channelName);

    UserIdentity getBotForPlatform(Platform platform);

    Optional<UserIdentity> findBotForPlatform(Platform platform);

    Map<Identity, UserIdentity> findBots();

    UserIdentity saveUserInfo(UserInfo userInfo);
}
