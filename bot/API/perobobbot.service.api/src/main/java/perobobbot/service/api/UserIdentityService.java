package perobobbot.service.api;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import perobobbot.api.Id;
import perobobbot.api.Identity;
import perobobbot.api.JoinedChannelProvider;
import perobobbot.api.UserInfo;
import perobobbot.api.data.JoinedChannel;
import perobobbot.api.data.Platform;
import perobobbot.api.data.UnknownUserId;
import perobobbot.api.data.UserIdentity;

import java.util.Optional;

public interface UserIdentityService extends JoinedChannelProvider {

    int VERSION = 1;

    /**
     * @param userIdentityId the id of the user identity
     * @return the {@link UserIdentity} associated with the provided id
     * @throws perobobbot.api.data.UnknownUserIdentityId if no userIdentity exists for the provided id
     */
    @NonNull UserIdentity getUserIdentity(long userIdentityId);

    @NonNull Optional<UserInfo> findUserInfo(@NonNull Identity identity);

    default @NonNull UserIdentity getUserIdentity(@NonNull Id id) {
        return findUserIdentity(id).orElseThrow(() -> new UnknownUserId(id));
    }

    @NonNull Optional<UserIdentity> findUserIdentity(@NonNull Id id);

    /**
     * List all the user identities
     *
     * @param page the page number
     * @param size the size of the page (-1 for no size)
     * @return a list of the {@link UserIdentity} for the provided page information
     */
    @NonNull ImmutableList<UserIdentity> listUserIdentities(int page, int size);

    @NonNull ImmutableList<UserIdentity> listUserIdentities(@NonNull Platform platform, int page, int size);

    @NonNull JoinedChannel joinChannel(long userIdentityId, @NonNull String channelName, boolean readOnly);

    void partChannel(long userIdentityId, @NonNull String channelName);

    @NonNull ImmutableSet<JoinedChannel> listJoinedChannels(long userIdentityId);

    @NonNull JoinedChannel getJoinedChannel(long userIdentityId, @NonNull String channelName);

    @NonNull UserIdentity getBotForPlatform(@NonNull Platform platform);

    @NonNull Optional<UserIdentity> findBotForPlatform(@NonNull Platform platform);

    @NonNull ImmutableMap<Identity, UserIdentity> findBots();

    @NonNull UserIdentity saveUserInfo(@NonNull UserInfo userInfo);
}
