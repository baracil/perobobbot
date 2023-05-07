package perobobbot.chat.api;

import java.util.concurrent.CompletionStage;

public interface Chat extends ChatIO {

    CompletionStage<Chat> requestConnection();

    void connect() throws InterruptedException;

    CompletionStage<?> requestDisconnection();
}
