package perobobbot.launcher.holder;

import fpc.tools.lang.ValueHolder;
import fpc.tools.lang.ValueHolderThreadLocal;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import perobobbot.oauth.api.Header;
import perobobbot.oauth.api.HeaderHolder;

@Singleton
@Slf4j
public class ThreadLocalHeaderHolder extends ValueHolderThreadLocal<Header> implements HeaderHolder {

    private static final ThreadLocal<ValueHolder<Header>> THREAD_LOCAL_HOLDER = new ThreadLocal<>();

    public ThreadLocalHeaderHolder() {
        super(THREAD_LOCAL_HOLDER);
    }

}
