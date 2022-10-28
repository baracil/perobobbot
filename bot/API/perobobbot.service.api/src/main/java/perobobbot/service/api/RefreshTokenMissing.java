package perobobbot.service.api;

public class RefreshTokenMissing extends DomainException {

    public RefreshTokenMissing() {
        super("Refresh token is missing");
    }
}
