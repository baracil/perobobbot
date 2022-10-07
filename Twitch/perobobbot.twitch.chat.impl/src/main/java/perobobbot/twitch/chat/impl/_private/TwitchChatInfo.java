package perobobbot.twitch.chat.impl._private;

import fpc.tools.chat.ReconnectionPolicy;
import fpc.tools.lang.Instants;
import fpc.tools.state.chat.ChatInfo;
import fpc.tools.state.chat.ChatStateListener;
import lombok.Builder;
import lombok.NonNull;
import perobobbot.twitch.chat.TwitchBandwidth;
import perobobbot.twitch.chat.TwitchConstants;
import perobobbot.twitch.chat.message.from.MessageFromTwitch;

import java.time.Duration;

public class TwitchChatInfo extends ChatInfo<MessageFromTwitch> {


    @Builder
    public TwitchChatInfo(@NonNull String nick,
                          @NonNull Instants instants,
                          @NonNull ChatStateListener<MessageFromTwitch> chatStateListener,
                          @NonNull ReconnectionPolicy reconnectionPolicy,
                          @NonNull TwitchBandwidth twitchBandwidth) {
        super(
                TwitchConstants.TWITCH_CHAT_URI,
                new TwitchMatcher(nick),
                new TwitchMessageConverter(),
                reconnectionPolicy,
                instants,
                chatStateListener,
                twitchBandwidth);
    }

    public static @NonNull TwitchChatInfoBuilder builder(@NonNull String nick, @NonNull Instants instants, @NonNull ChatStateListener<MessageFromTwitch> chatStateListener) {
        return new TwitchChatInfoBuilder()
                .nick(nick)
                .instants(instants)
                .chatStateListener(chatStateListener)
                .reconnectionPolicy(ReconnectionPolicy.withMaximalNumberOfAttempts(3, i -> Duration.ofSeconds(10)))
                .twitchBandwidth(TwitchBandwidth.REGULAR);
    }

}
