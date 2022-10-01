package perobobbot.api.oauth;

import lombok.Getter;
import lombok.NonNull;

public class NoUserOrApplicationTokenAvailable extends OAuthException {

    @Getter
    private final @NonNull OAuthAccessMode mode;

    public NoUserOrApplicationTokenAvailable(@NonNull OAuthAccessMode mode) {
        super("Could not get any token for mode '"+mode+"'");
        this.mode = mode;
    }
}
