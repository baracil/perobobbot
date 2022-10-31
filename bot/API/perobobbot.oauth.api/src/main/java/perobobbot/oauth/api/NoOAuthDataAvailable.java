package perobobbot.oauth.api;

import lombok.Getter;
import lombok.NonNull;
import perobobbot.api.data.Platform;

public class NoOAuthDataAvailable extends OAuthException {

    @Getter
    private final @NonNull Platform platform;

    public NoOAuthDataAvailable(@NonNull Platform platform) {
        super("No OAuthData has been set in the OAuthHolder for platform '"+platform+"'");
        this.platform = platform;
    }
}
