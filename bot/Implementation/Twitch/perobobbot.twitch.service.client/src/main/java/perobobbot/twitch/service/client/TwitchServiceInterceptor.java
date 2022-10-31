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
import perobobbot.oauth.api.AuthHolder;
import perobobbot.oauth.api.HeaderHolder;
import perobobbot.oauth.api.OAuthAccessMode;
import perobobbot.oauth.api.OAuthData;
import perobobbot.twitch.api.Twitch;
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
        return new Interception(context).intercept();
    }

    @RequiredArgsConstructor
    private class Interception {
        private final @NonNull MethodInvocationContext<Object, Object> context;
        private OAuthAccessMode accessMode;
        private OAuthData oAuthData;
        private TryResult<RuntimeException, Optional<Object>> result;

        public Object intercept() {
            this.extractAccessMode();
            if (hasNoAccessMode()) {
                this.proceedWithoutAccessMode();
            } else {
                this.getOAuthData();
                this.proceedWithAccessMode();
            }
            return result.throwIfFailure().orElse(null);
        }

        private void extractAccessMode() {
            final var userAuth = context.getAnnotation(UserAuth.class);
            final var appAuth = context.getAnnotation(AppAuth.class);
            accessMode = OAuthAccessMode.evaluateMode(userAuth != null, appAuth != null).orElse(null);
        }

        private boolean hasNoAccessMode() {
            return accessMode == null;
        }

        private void proceedWithoutAccessMode() {
            result = proceed(context);
        }

        private void getOAuthData() {
            oAuthData = authHolder.get(Twitch.PLATFORM);
        }

        private void proceedWithAccessMode() {
            assert oAuthData != null;
            assert accessMode != null;
            final var wrappedMethod = new WrappedMethod(context, accessMode, oAuthData);
            result = callWithOAuth(wrappedMethod, false);
        }
    }


    private @NonNull TryResult<RuntimeException, Optional<Object>> callWithOAuth(@NonNull WrappedMethod method,
                                                                                 boolean refreshFirst
    ) {
        final var callerResult = method.call(refreshFirst);
        if (refreshFirst || callerResult.getEither().merge(this::isNotDuToInvalidToken, s -> true)) {
            //cannot refresh anymore
            return callerResult;
        }
        LOG.info("Retry call with refreshed token");
        return callWithOAuth(method, true);
    }

    private boolean isNotDuToInvalidToken(@NonNull RuntimeException error) {
        final var msg = error.getMessage();
        if (msg == null) {
            return true;
        }
        return !(msg.contains("invalid") && msg.contains("oauth") && msg.contains("token"));
    }

    @RequiredArgsConstructor
    private class WrappedMethod {

        private final @NonNull MethodInvocationContext<Object, Object> context;
        @Getter
        private final @NonNull OAuthAccessMode oAuthAccessMode;
        private final @NonNull OAuthData oauthData;


        public TryResult<RuntimeException, Optional<Object>> call(boolean tryWithRefresh) {

            if (tryWithRefresh) {
                oauthData.refresh(oAuthAccessMode);
            }

            final var clientId = oauthData.getClientId();
            final var accessToken = oauthData.getAccessToken(oAuthAccessMode);

            final var headerBuilder = headerHolder.withPrevious();

            headerBuilder.put("Client-Id", clientId);
            headerBuilder.put("Authorization", "Bearer " + accessToken.value());

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
