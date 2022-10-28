package perobobbot.twitch.chat.impl._private;

import fpc.tools.chat.ReconnectionPolicy;
import fpc.tools.lang.Instants;
import fpc.tools.state.chat.ChatInfo;
import fpc.tools.state.chat.ChatStateListener;
import fpc.tools.state.chat.ChatStateManager;
import lombok.NonNull;
import perobobbot.twitch.chat.TwitchBandwidth;
import perobobbot.twitch.chat.TwitchConstants;
import perobobbot.twitch.chat.message.from.MessageFromTwitch;

import java.time.Duration;

public class TwitchChatStateManager extends ChatStateManager<MessageFromTwitch> {

    public TwitchChatStateManager(
            @NonNull String nick,
            @NonNull ChatStateListener<MessageFromTwitch> chatListener,
            @NonNull Instants instants) {
        super(new ChatInfo<>(
                TwitchConstants.TWITCH_CHAT_URI,
                new TwitchMatcher(nick),
                new TwitchMessageConverter(),
                ReconnectionPolicy.withMaximalNumberOfAttempts(3, i -> Duration.ofSeconds(10)),
                instants,
                chatListener,
                TwitchBandwidth.REGULAR
        ));
    }
}
