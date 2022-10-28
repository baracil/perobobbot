package perobobbot.oauth.api;

public class NoOAuthDataAvailable extends OAuthException {

    public NoOAuthDataAvailable() {
        super("No OAuthData has been set in the OAuthHolder");
    }
}
