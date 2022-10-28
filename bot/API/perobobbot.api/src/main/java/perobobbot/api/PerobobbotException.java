package perobobbot.api;

public class PerobobbotException extends RuntimeException {

    public PerobobbotException() {
    }

    public PerobobbotException(String message) {
        super(message);
    }

    public PerobobbotException(String message, Throwable cause) {
        super(message, cause);
    }

    public PerobobbotException(Throwable cause) {
        super(cause);
    }

    public PerobobbotException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
