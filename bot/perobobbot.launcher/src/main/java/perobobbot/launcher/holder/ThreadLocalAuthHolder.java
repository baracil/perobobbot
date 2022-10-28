package perobobbot.launcher.holder;

import fpc.tools.lang.ValueHolder;
import fpc.tools.lang.ValueHolderThreadLocal;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import perobobbot.oauth.api.AuthHolder;
import perobobbot.oauth.api.OAuthData;

@Singleton
@Slf4j
public class ThreadLocalAuthHolder extends ValueHolderThreadLocal<OAuthData> implements AuthHolder {

    private static final ThreadLocal<ValueHolder<OAuthData>> THREAD_LOCAL_HOLDER = new ThreadLocal<>();

    public ThreadLocalAuthHolder() {
        super(THREAD_LOCAL_HOLDER);
    }

}
