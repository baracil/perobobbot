package perobobbot.oauth.api;

import lombok.Getter;

public class NoUserOrApplicationTokenAvailable extends OAuthException {

    @Getter
    private final OAuthAccessMode mode;

    public NoUserOrApplicationTokenAvailable(OAuthAccessMode mode) {
        super("Could not get any token for mode '"+mode+"'");
        this.mode = mode;
    }
}
