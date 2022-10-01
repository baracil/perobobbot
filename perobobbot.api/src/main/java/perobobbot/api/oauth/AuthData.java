package perobobbot.api.oauth;

import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import perobobbot.api.data.Platform;
import perobobbot.api.data.view.AccessTokenProvider;
import perobobbot.api.data.view.Application;
import perobobbot.api.data.view.ApplicationToken;
import perobobbot.api.data.view.UserToken;

import java.util.Optional;

@Value
@RequiredArgsConstructor
@Builder(toBuilder = true)
public class AuthData {
    Application.@NonNull Decrypted application;
    UserToken.Decrypted userToken;
    ApplicationToken.Decrypted applicationToken;

    public @NonNull AuthData withUserToken(@NonNull UserToken.Decrypted userToken) {
        return toBuilder().userToken(userToken).build();
    }

    public @NonNull AuthData withApplicationToken(@NonNull ApplicationToken.Decrypted applicationToken) {
        return toBuilder().applicationToken(applicationToken).build();
    }


    public @NonNull Optional<UserToken.Decrypted> getUserToken() {
        return Optional.ofNullable(userToken);
    }

    public @NonNull Optional<ApplicationToken.Decrypted> getApplicationToken() {
        return Optional.ofNullable(applicationToken);
    }

    public @NonNull AccessTokenProvider getAccessToken(@NonNull OAuthAccessMode mode) {
        final var provider = switch (mode) {
            case APP_ONLY -> applicationToken;
            case USER_ONLY -> userToken;
            case BOTH_ACCEPTED -> applicationToken==null?userToken:applicationToken;
        };

        if (provider == null) {
            throw new NoUserOrApplicationTokenAvailable(mode);
        }
        return provider;
    }

    public @NonNull String getClientId() {
        return application.clientId();
    }

    public @NonNull Platform getPlatform() {
        return application.platform();
    }
}
