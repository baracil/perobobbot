package perobobbot.launcher.holder;

import fpc.tools.lang.ValueHolder;
import fpc.tools.lang.ValueHolderThreadLocal;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import perobobbot.api.oauth.AData;
import perobobbot.api.oauth.AuthData;
import perobobbot.api.oauth.AuthHolder;

@Singleton
@Slf4j
public class ThreadLocalAuthHolder extends ValueHolderThreadLocal<AData> implements AuthHolder {

    private static final ThreadLocal<ValueHolder<AData>> THREAD_LOCAL_HOLDER = new ThreadLocal<>();

    public ThreadLocalAuthHolder() {
        super(THREAD_LOCAL_HOLDER);
    }

}
