package perobobbot.launcher.holder;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.api.oauth.TokenData;
import perobobbot.api.oauth.TokenHolder;
import perobobbot.oauth.OAuthManager;
import perobobbot.service.api.UserTokenService;

import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;

@RequiredArgsConstructor
public class SimpleTokenHolder implements TokenHolder {

    private final @NonNull OAuthManager oAuthManager;
    private final @NonNull UserTokenService userTokenService;
    private final @NonNull Queue<TokenData> queue = new LinkedList<>();

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    @Override
    public void refreshUserToken() {
        final var data = queue.poll();
        if (data == null) {
            return;
        }
        final var refreshedToken = oAuthManager.refresh(data.platform(), data.refreshToken());
        final var refreshedData = data.withNewToken(refreshedToken);

        userTokenService.setUserToken(refreshedToken);
        push(refreshedData);
    }

    @Override
    public @NonNull Optional<TokenData> get() {
        return Optional.ofNullable(queue.peek());
    }

    @Override
    public void push(@NonNull TokenData data) {
        queue.add(data);
    }

    @Override
    public void pop() {
        queue.poll();
    }
}
