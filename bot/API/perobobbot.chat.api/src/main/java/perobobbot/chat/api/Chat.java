package perobobbot.chat.api;

import lombok.NonNull;

import java.util.concurrent.CompletionStage;

public interface Chat extends ChatIO {

    @NonNull CompletionStage<Chat> requestConnection();

    void connect() throws InterruptedException;

    @NonNull CompletionStage<?> requestDisconnection();
}
