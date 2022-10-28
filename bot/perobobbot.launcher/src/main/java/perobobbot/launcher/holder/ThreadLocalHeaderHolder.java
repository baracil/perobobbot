package perobobbot.launcher.holder;

import com.google.common.collect.ImmutableMap;
import fpc.tools.lang.ValueHolder;
import fpc.tools.lang.ValueHolderThreadLocal;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import perobobbot.oauth.api.HeaderHolder;

@Singleton
@Slf4j
public class ThreadLocalHeaderHolder extends ValueHolderThreadLocal<ImmutableMap<String, String>> implements HeaderHolder {

    private static final ThreadLocal<ValueHolder<ImmutableMap<String, String>>> THREAD_LOCAL_HOLDER = new ThreadLocal<>();

    public ThreadLocalHeaderHolder() {
        super(THREAD_LOCAL_HOLDER);
    }

}
