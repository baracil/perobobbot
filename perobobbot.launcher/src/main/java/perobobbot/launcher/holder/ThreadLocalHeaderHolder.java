package perobobbot.launcher.holder;

import com.google.common.collect.ImmutableMap;
import io.micronaut.core.type.MutableHeaders;
import jakarta.inject.Singleton;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import perobobbot.api.oauth.HeaderHolder;

@Singleton
@Slf4j
public class ThreadLocalHeaderHolder extends ThreadLocalDataHolder<HeaderHolder, ImmutableMap<String, String>> implements HeaderHolder {

    private static final ThreadLocal<HeaderHolder> THREAD_LOCAL_HOLDER = new ThreadLocal<>();

    public ThreadLocalHeaderHolder() {
        super(THREAD_LOCAL_HOLDER);
    }

    @Override
    protected @NonNull HeaderHolder createInitial() {
        return new SimpleHeaderHolder();
    }


    @Override
    public void setHeaders(@NonNull MutableHeaders headers) {
        System.out.println(">>> SET HEADERS on Thread "+Thread.currentThread().getName());
        getHolder().ifPresent(h -> h.setHeaders(headers));
    }
}
