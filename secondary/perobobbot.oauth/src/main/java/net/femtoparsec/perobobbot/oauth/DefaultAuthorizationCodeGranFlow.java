package net.femtoparsec.perobobbot.oauth;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.api.data.view.UserToken;
import perobobbot.oauth.AuthorizationCodeGranFlow;
import perobobbot.oauth.Failure;

import java.net.URI;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

@RequiredArgsConstructor
public class DefaultAuthorizationCodeGranFlow implements AuthorizationCodeGranFlow {

    @Getter
    private final @NonNull URI uri;
    private final @NonNull String state;
    private final @NonNull DefaultOAuthManager defaultOAuthManager;
    @Getter
    private final @NonNull CompletableFuture<UserToken> future = new CompletableFuture<>();

    @Override
    public void whenComplete(Consumer<UserToken> onResult, @NonNull Consumer<Throwable> onError) {
        future.whenComplete((r,t) -> {
            if (t != null) {
                onError.accept(t);
            } else {
                onResult.accept(r);
            }
        });
    }

    @Override
    public void setFailed(@NonNull Failure failure) {
        defaultOAuthManager.failFlow(state,failure);
    }


}
