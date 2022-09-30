package perobobbot.service.api;

import perobobbot.api.PerobobbotException;

public class RefreshTokenMissing extends PerobobbotException {

    public RefreshTokenMissing() {
        super("Refresh token is missing");
    }
}
