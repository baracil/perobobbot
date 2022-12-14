package perobobbot.launcher.oauth;

import com.google.common.collect.ImmutableMap;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.api.Id;
import perobobbot.api.data.Platform;
import perobobbot.oauth.api.AuthHolder;
import perobobbot.oauth.api.NoOAuthDataAvailable;
import perobobbot.oauth.api.OAuthData;
import perobobbot.oauth.api.OAuthDataFactory;
import perobobbot.service.api.UserIdentityService;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class MainAuthHolder implements AuthHolder {

    private final static ThreadLocal<Map<Platform,OAuthData>> THREAD_LOCAL = ThreadLocal.withInitial(HashMap::new);

    private @NonNull UserIdentityService userIdentityService;
    private @NonNull OAuthDataFactory oAuthDataFactory;
    private @NonNull ImmutableMap<Platform,OAuthData> defaultOAuthData;

    @Override
    public @NonNull OAuthData get(@NonNull Platform platform) {
        final var values = THREAD_LOCAL.get();
        final var value = values.get(platform);
        if (value != null) {
            return value;
        }
        final var defaultValue = defaultOAuthData.get(platform);
        if (defaultValue != null) {
            return defaultValue;
        }
        throw new NoOAuthDataAvailable(platform);
    }

    @Override
    public void executeWith(@NonNull Id id, @NonNull Runnable action) {
        final var current = THREAD_LOCAL.get().get(id.platform());
        try {
            setId(id);
            action.run();
        } finally {
            final var map = THREAD_LOCAL.get();
            if (current != null) {
                map.put(id.platform(),current);
            } else {
                THREAD_LOCAL.remove();
            }
        }
    }

    @Override
    public void setId(@NonNull Id id) {
        final var userIdentity = userIdentityService.getUserIdentity(id);
        final var oAuthData = oAuthDataFactory.create(userIdentity.toUserInfo());
        THREAD_LOCAL.get().put(id.platform(),oAuthData);
    }

    @Override
    public void clearId(@NonNull Platform platform) {
        final var map = THREAD_LOCAL.get();
        map.remove(platform);
        if (map.isEmpty()) {
            THREAD_LOCAL.remove();
        }
    }

}
