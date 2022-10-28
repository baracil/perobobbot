package perobobbot.oauth.impl;

import com.google.common.collect.ImmutableMap;
import lombok.NonNull;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public sealed interface CallbackInfo {

    @NonNull String state();

    static Optional<CallbackInfo> parse(@NonNull ImmutableMap<String, String> queryValues) {
        final var log = LoggerFactory.getLogger(CallbackInfo.class);
        final var state = queryValues.get("state");
        final var error = queryValues.get("error");
        final var code = queryValues.get("code");

        if (state == null) {
            log.warn("Could not get 'state' from queryValues");
            return Optional.empty();
        }
        if (error != null) {
            return Optional.of(new Error(state, queryValues.getOrDefault("error_description", "no description")));
        }

        if (code == null) {
            log.warn("Authentication failed : no code available");
            return Optional.empty();
        }

        return Optional.of(new Success(state, code));

    }


    record Error(@NonNull String state, @NonNull String description) implements CallbackInfo {
    }

    record Success(@NonNull String state, @NonNull String code) implements CallbackInfo {
    }
}
