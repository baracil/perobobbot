package perobobbot.twitch.chat;

import lombok.NonNull;

import java.util.concurrent.CompletionStage;

public interface TwitchChat extends TwitchIO {

    @NonNull CompletionStage<TwitchChat> requestConnection();

    void connect() throws InterruptedException;

    void requestDisconnection();


}
