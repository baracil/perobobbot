package perobobbot.service.api;

import lombok.Getter;
import lombok.NonNull;
import perobobbot.api.data.Platform;

@Getter
public class UnknownLogin extends DomainException {

    private final @NonNull Platform platform;
    private final @NonNull String login;

    public UnknownLogin(@NonNull Platform platform, @NonNull String login) {
        super("Could not find any user on platform '" + platform + "' with login '" + login + "'");
        this.platform = platform;
        this.login = login;
    }
}
