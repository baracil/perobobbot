package perobobbot.launcher.holder;

import jakarta.inject.Singleton;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import perobobbot.api.oauth.TokenData;
import perobobbot.api.oauth.TokenHolder;
import perobobbot.oauth.OAuthManager;
import perobobbot.service.api.UserTokenService;

import java.util.Optional;

@Singleton
@Slf4j
public class ThreadLocalTokenHolder extends ThreadLocalDataHolder<TokenHolder,TokenData> implements TokenHolder {

    private static final ThreadLocal<TokenHolder> THREAD_LOCAL_HOLDER = new ThreadLocal<>();

    private final @NonNull OAuthManager oAuthManager;
    private final @NonNull UserTokenService userTokenService;

    public ThreadLocalTokenHolder(@NonNull OAuthManager oAuthManager, @NonNull UserTokenService userTokenService) {
        super(THREAD_LOCAL_HOLDER);
        this.oAuthManager = oAuthManager;
        this.userTokenService = userTokenService;
    }

    @Override
    public @NonNull Optional<TokenData> get() {
        System.out.println(">>> GET  on Thread        "+Thread.currentThread().getName());
        return getHolder().flatMap(TokenHolder::get);
    }

    @Override
    protected @NonNull TokenHolder createInitial() {
        return new SimpleTokenHolder(oAuthManager,userTokenService);
    }

    @Override
    public void refreshUserToken() {
        getHolder().ifPresent(TokenHolder::refreshUserToken);
    }

 }
