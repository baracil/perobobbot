package perobobbot.api.oauth;

import perobobbot.api.PerobobbotException;

@SuppressWarnings("unused")
public class OAuthException extends PerobobbotException {

    public OAuthException() {
    }

    public OAuthException(String message) {
        super(message);
    }

    public OAuthException(String message, Throwable cause) {
        super(message, cause);
    }

    public OAuthException(Throwable cause) {
        super(cause);
    }

    public OAuthException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
