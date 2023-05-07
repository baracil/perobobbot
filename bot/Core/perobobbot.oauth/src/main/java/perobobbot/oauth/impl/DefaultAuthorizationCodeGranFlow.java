package perobobbot.oauth.impl;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import perobobbot.api.data.TokenWithIdentity;
import perobobbot.oauth.api.AuthorizationCodeGranFlow;
import perobobbot.oauth.api.Failure;
import perobobbot.oauth.api.FlowFailure;

import java.net.URI;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

@RequiredArgsConstructor
public class DefaultAuthorizationCodeGranFlow implements AuthorizationCodeGranFlow {

    @Getter
    private final URI uri;
    @Getter
    private final String state;
    private final Consumer<TokenWithIdentity> onResult;
    private final Consumer<Throwable> onError;
    @Getter
    private final CompletableFuture<TokenWithIdentity> future = new CompletableFuture<>();

    {
        future.whenComplete(this::handleFutureCompletion);
    }


    private void handleFutureCompletion(TokenWithIdentity token, Throwable throwable) {
        if (throwable != null) {
            onError.accept(throwable);
        } else {
            onResult.accept(token);
        }
    }

    @Override
    public void completeWithError(Failure failure) {
        this.future.completeExceptionally(new FlowFailure(failure));
    }

    @Override
    public void completWithSuccess(TokenWithIdentity token) {
        this.future.complete(token);
    }



}
