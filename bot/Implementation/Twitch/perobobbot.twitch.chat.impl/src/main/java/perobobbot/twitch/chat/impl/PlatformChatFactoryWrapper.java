package perobobbot.twitch.chat.impl;

import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import perobobbot.api.data.Platform;
import perobobbot.api.data.UserIdentity;
import perobobbot.chat.api.Chat;
import perobobbot.chat.api.PlatformChatFactory;
import perobobbot.twitch.api.Twitch;
import perobobbot.twitch.chat.TwitchChatFactory;
import perobobbot.twitch.chat.impl._private.wrapping.TwitchChatWrapper;

/**
 * Adapt a {@link TwitchChatFactory} to a {@link PlatformChatFactory}
 */
@RequiredArgsConstructor
@Singleton
public class PlatformChatFactoryWrapper implements PlatformChatFactory {

    private final TwitchChatFactory twitchChatFactory;

    @Override
    public Platform getPlatform() {
        return Twitch.PLATFORM;
    }

    @Override
    public Chat create(UserIdentity userIdentity) {
        return new TwitchChatWrapper(twitchChatFactory.create(userIdentity));
    }
}
