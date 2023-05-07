package perobobbot.bus.api;

import java.io.Closeable;
import java.util.concurrent.CompletionStage;

public interface Consumer<T> extends Closeable {

    Message<T> receive() throws InterruptedException;

    CompletionStage<Message<T>> receiveAsync();

}
