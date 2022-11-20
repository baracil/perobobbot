package perobobbot.twitch.chat.impl;

import fpc.tools.lang.Instants;
import jakarta.inject.Singleton;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.api.JoinedChannelProvider;
import perobobbot.api.bus.Bus;
import perobobbot.api.data.UserIdentity;
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

    @Override
    public @NonNull TwitchChat create(@NonNull UserIdentity userIdentity) {
        return new DefaultTwitchChat(
                userIdentity,
                authDataFactory.create(userIdentity),
                joinedChannelProvider.forUserIdentity(userIdentity.id()),
                instants,
                bus);
    }
}
