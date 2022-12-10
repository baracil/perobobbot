package perobobbot.twitch.chat.impl;

import fpc.tools.lang.Instants;
import jakarta.inject.Singleton;
import lombok.NonNull;
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

    private final @NonNull Bus bus;
    private final @NonNull OAuthDataFactory authDataFactory;
    private final @NonNull JoinedChannelProvider joinedChannelProvider;
    private final @NonNull Instants instants;
    private final @NonNull UserIdentityProvider userIdentityProvider;

    @Override
    public @NonNull TwitchChat create(@NonNull UserIdentity userIdentity) {
        return new DefaultTwitchChat(
                userIdentity,
                authDataFactory.create(userIdentity.toUserInfo()),
                joinedChannelProvider.forUserIdentity(userIdentity.id()),
                instants,
                bus, userIdentityProvider);
    }
}
