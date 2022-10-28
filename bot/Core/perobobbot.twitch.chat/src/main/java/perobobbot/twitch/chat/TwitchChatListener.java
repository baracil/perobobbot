package perobobbot.twitch.chat;

import lombok.NonNull;
import perobobbot.twitch.chat.message.from.MessageFromTwitch;
import perobobbot.twitch.chat.message.to.MessageToTwitch;

import java.time.Instant;

public interface TwitchChatListener {

    void onConnected();

    void onDisconnected();

    void onPostedMessage(@NonNull Instant postInstant, @NonNull MessageToTwitch message);

    void onReceivedMessage(@NonNull Instant receptionInstant, @NonNull MessageFromTwitch message);


}
