package perobobbot.oauth.api;

import lombok.Getter;
import perobobbot.api.data.Platform;

public class NoOAuthDataAvailable extends OAuthException {

    @Getter
    private final Platform platform;

    public NoOAuthDataAvailable(Platform platform) {
        super("No OAuthData has been set in the OAuthHolder for platform '"+platform+"'");
        this.platform = platform;
    }
}
