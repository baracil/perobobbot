package perobobbot.twitch.chat.impl;

import fpc.tools.lang.Instants;
import jakarta.inject.Singleton;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.api.ChannelProvider;
import perobobbot.api.Identification;
import perobobbot.api.oauth.OAuthDataFactory;
import perobobbot.twitch.chat.TwitchChat;
import perobobbot.twitch.chat.TwitchChatFactory;

@Singleton
@RequiredArgsConstructor
public class DefaultTwitchChatFactory implements TwitchChatFactory {

    private final @NonNull OAuthDataFactory authDataFactory;
    private final @NonNull ChannelProvider channelProvider;
    private final @NonNull Instants instants;

    @Override
    public @NonNull TwitchChat create(@NonNull Identification identification, @NonNull String login) {
        return new DefaultTwitchChat(authDataFactory.create(identification,login),channelProvider.forUserIdentity(identification),instants);
    }
}
