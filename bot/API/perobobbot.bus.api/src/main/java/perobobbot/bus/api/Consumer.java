package perobobbot.bus.api;

import lombok.NonNull;

import java.io.Closeable;
import java.util.concurrent.CompletionStage;

public interface Consumer<T> extends Closeable {

    @NonNull Message<T> receive() throws InterruptedException;

    @NonNull CompletionStage<Message<T>> receiveAsync();

}
