package perobobbot.twitch.chat;

import perobobbot.twitch.chat.message.from.MessageFromTwitch;
import perobobbot.twitch.chat.message.to.MessageToTwitch;

import java.time.Instant;

public interface TwitchChatListener {

    void onConnected();

    void onDisconnected();

    void onPostedMessage(Instant postInstant, MessageToTwitch message);

    void onReceivedMessage(Instant receptionInstant, MessageFromTwitch message);


}
