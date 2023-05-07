package perobobbot.twitch.chat.impl;

import fpc.tools.lang.Instants;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import perobobbot.api.JoinedChannelProvider;
import perobobbot.api.data.UserIdentity;
import perobobbot.api.data.UserIdentityProvider;
import perobobbot.bus.api.Bus;
import perobobbot.oauth.api.OAuthDataFactory;
import perobobbot.twitch.chat.TwitchChat;
import perobobbot.twitch.chat.TwitchChatFactory;
import perobobbot.twitch.chat.impl._private.DefaultTwitchChat;

@Singleton
@RequiredArgsConstructor
public class DefaultTwitchChatFactory implements TwitchChatFactory {

    private final Bus bus;
    private final OAuthDataFactory authDataFactory;
    private final JoinedChannelProvider joinedChannelProvider;
    private final Instants instants;
    private final UserIdentityProvider userIdentityProvider;

    @Override
    public TwitchChat create(UserIdentity userIdentity) {
        return new DefaultTwitchChat(
                userIdentity,
                authDataFactory.create(userIdentity.toUserInfo()),
                joinedChannelProvider.forUserIdentity(userIdentity.id()),
                instants,
                bus, userIdentityProvider);
    }
}
