package perobobbot.api;

import lombok.NonNull;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public interface PerobobbotExecutors {

    @NonNull ScheduledFuture<?> schedule(Runnable command, long delay, TimeUnit unit);

    @NonNull <V> ScheduledFuture<V> schedule(Callable<V> callable, long delay, TimeUnit unit);

    @NonNull ScheduledFuture<?> scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit);

    @NonNull ScheduledFuture<?> scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit);

    @NonNull <T> Future<T> submit(Callable<T> task);

    @NonNull <T> Future<T> submit(Runnable task, T result);

    @NonNull Future<?> submit(Runnable task);

    default @NonNull <T> Future<?> submit(Consumer<T> task, T value) {
        return submit(() -> task.accept(value));
    }
}
