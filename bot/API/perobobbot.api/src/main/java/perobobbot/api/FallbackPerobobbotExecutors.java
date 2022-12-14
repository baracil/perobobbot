package perobobbot.api;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.micronaut.retry.annotation.Fallback;
import jakarta.annotation.PreDestroy;
import jakarta.inject.Singleton;
import lombok.NonNull;

import java.util.concurrent.*;

@Singleton
@Fallback
public class FallbackPerobobbotExecutors implements PerobobbotExecutors {

    private final @NonNull ExecutorService executorService;
    private final @NonNull ScheduledExecutorService scheduledExecutorService;

    public FallbackPerobobbotExecutors() {
        this.executorService = Executors.newCachedThreadPool(new ThreadFactoryBuilder().setDaemon(true).setNameFormat("Pero CachedThreadPool-%d").build());
        this.scheduledExecutorService = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors(),
                new ThreadFactoryBuilder().setDaemon(true).setNameFormat("Pero ScheduledThreadPool-%d").build());
    }


    @PreDestroy
    public void shutdown() {
        executorService.shutdown();
        scheduledExecutorService.shutdown();
    }

    @Override
    @NonNull
    public ScheduledFuture<?> schedule(Runnable command, long delay, TimeUnit unit) {
        return scheduledExecutorService.schedule(command, delay, unit);
    }

    @Override
    @NonNull
    public <V> ScheduledFuture<V> schedule(Callable<V> callable, long delay, TimeUnit unit) {
        return scheduledExecutorService.schedule(callable, delay, unit);
    }

    @Override
    @NonNull
    public ScheduledFuture<?> scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit) {
        return scheduledExecutorService.scheduleAtFixedRate(command, initialDelay, period, unit);
    }

    @Override
    @NonNull
    public ScheduledFuture<?> scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit) {
        return scheduledExecutorService.scheduleWithFixedDelay(command, initialDelay, delay, unit);
    }

    @Override
    @NonNull
    public <T> Future<T> submit(Callable<T> task) {
        return executorService.submit(task);
    }

    @Override
    @NonNull
    public <T> Future<T> submit(Runnable task, T result) {
        return executorService.submit(task, result);
    }

    @Override
    @NonNull
    public Future<?> submit(Runnable task) {
        return executorService.submit(task);
    }
}
