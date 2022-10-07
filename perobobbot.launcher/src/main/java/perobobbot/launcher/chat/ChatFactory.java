package perobobbot.launcher.chat;

import fpc.tools.micronaut.EagerInit;
import io.micronaut.context.annotation.Factory;
import jakarta.inject.Singleton;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.service.api.UserIdentityService;
import perobobbot.twitch.api.Twitch;
import perobobbot.twitch.chat.TwitchChat;
import perobobbot.twitch.chat.TwitchChatFactory;

import java.util.concurrent.ExecutionException;

@Factory
@RequiredArgsConstructor
public class ChatFactory {

    private final @NonNull UserIdentityService userIdentityService;
    private final @NonNull TwitchChatFactory twitchChatFactory;

    @EagerInit
    @Singleton
    public @NonNull TwitchChat twitchChat() throws ExecutionException, InterruptedException {
        final var bot = userIdentityService.getBotForPlatform(Twitch.PLATFORM);
        final var chat = twitchChatFactory.create(bot.identification(), bot.login());
        chat.connect();
        return chat;
    }
}
