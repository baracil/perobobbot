package perobobbot.twitch.service.client;

import fpc.tools.fp.TryResult;
import io.micronaut.aop.InterceptorBean;
import io.micronaut.aop.MethodInterceptor;
import io.micronaut.aop.MethodInvocationContext;
import jakarta.inject.Singleton;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import perobobbot.oauth.api.*;
import perobobbot.twitch.service.api.AppAuth;
import perobobbot.twitch.service.api.UserAuth;

import java.util.Optional;

@Singleton
@InterceptorBean({UserAuth.class, AppAuth.class})
@RequiredArgsConstructor
@Slf4j
public class TwitchServiceInterceptor implements MethodInterceptor<Object, Object> {

    private final @NonNull AuthHolder authHolder;
    private final @NonNull HeaderHolder headerHolder;

    @Override
    public Object intercept(MethodInvocationContext<Object, Object> context) {
        final var userAuth = context.getAnnotation(UserAuth.class);
        final var appAuth = context.getAnnotation(AppAuth.class);
        final var mode = OAuthAccessMode.evaluateMode(userAuth != null, appAuth != null);
        final var adata = authHolder.getOrElseThrow(NoOAuthDataAvailable::new);

        final var result = mode.map(m -> callWithOAuth(new Caller(context, m, adata), false))
                               .orElseGet(() -> proceed(context));

        return result.throwIfFailure().orElse(null);
    }

    private @NonNull TryResult<RuntimeException, Optional<Object>> callWithOAuth(@NonNull Caller caller,
                                                                                 boolean refreshFirst
                                                                                 ) {

        final var callerResult = caller.call(refreshFirst);

        if (refreshFirst) {
            //cannot refresh anymore
            return callerResult;
        }
        LOG.info("Retry call with refreshed token");
        return callWithOAuth(caller,true);
    }

    @RequiredArgsConstructor
    private class Caller {

        private final @NonNull MethodInvocationContext<Object, Object> context;
        @Getter
        private final @NonNull OAuthAccessMode oAuthAccessMode;
        private final @NonNull OAuthData oauthData;


        public TryResult<RuntimeException, Optional<Object>> call(boolean tryWithRefresh) {

            if (tryWithRefresh) {
                oauthData.refresh(oAuthAccessMode);
            }

            final var authData = authHolder.getOrElseThrow(NoOAuthDataAvailable::new);

            final var clientId = authData.getClientId();
            final var accessToken = authData.getAccessToken(oAuthAccessMode);

            final var headerBuilder = headerHolder.withPrevious();

            headerBuilder.put("Client-Id", clientId);
            headerBuilder.put("Authorization", "Bearer " + accessToken);

            return headerHolder.callWith(headerBuilder.build(), () -> proceed(context));
        }

    }

    private static TryResult<RuntimeException, Optional<Object>> proceed(MethodInvocationContext<Object, Object> context) {
        try {
            return TryResult.success(Optional.ofNullable(context.proceed()));
        } catch (RuntimeException e) {
            LOG.warn("Failure when calling {} : '{}'", context.getDescription(true), e);
            return TryResult.failure(e);
        }
    }

}
