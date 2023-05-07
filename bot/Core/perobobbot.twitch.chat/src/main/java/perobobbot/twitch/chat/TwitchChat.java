package perobobbot.twitch.chat;

import java.util.concurrent.CompletionStage;

public interface TwitchChat extends TwitchIO {

    CompletionStage<TwitchChat> requestConnection();

    void connect() throws InterruptedException;

    CompletionStage<?> requestDisconnection();


}
