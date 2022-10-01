package perobobbot.launcher.holder;

import fpc.tools.lang.ValueHolder;
import fpc.tools.lang.ValueHolderThreadLocal;
import jakarta.inject.Singleton;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import perobobbot.api.oauth.AuthData;
import perobobbot.api.oauth.AuthHolder;
import perobobbot.api.oauth.OAuthAccessMode;
import perobobbot.api.oauth.OAuthRefresher;

@Singleton
@Slf4j
public class ThreadLocalAuthHolder extends ValueHolderThreadLocal<AuthData> implements AuthHolder {

    private static final ThreadLocal<ValueHolder<AuthData>> THREAD_LOCAL_HOLDER = new ThreadLocal<>();

    private final @NonNull OAuthRefresher oAuthRefresher;

    public ThreadLocalAuthHolder(@NonNull OAuthRefresher oAuthRefresher) {
        super(THREAD_LOCAL_HOLDER);
        this.oAuthRefresher = oAuthRefresher;
    }

    @Override
    public void refreshToken(@NonNull OAuthAccessMode oAuthAccessMode) {
        final var data = get().orElse(null);
        if (data == null) {
            return;
        }
        final var refreshedData = oAuthRefresher.refresh(data,oAuthAccessMode);
        popAndPush(refreshedData);
    }

    @Override
    public void prepareToken(@NonNull OAuthAccessMode oAuthAccessMode) {
        final var data = get().orElse(null);
        if (data == null) {
            return;
        }
        final var refreshedData = oAuthRefresher.prepare(data,oAuthAccessMode);
        popAndPush(refreshedData);
    }
}
