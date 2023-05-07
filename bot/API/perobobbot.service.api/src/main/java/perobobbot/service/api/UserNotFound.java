package perobobbot.service.api;

import lombok.Getter;
import perobobbot.api.Identity;

@Getter
public class UserNotFound extends DomainException {

    private final Identity identity;

    public UserNotFound(Identity identity) {
        super("Could not find any user on platform '"+ identity.platform()+"' with user Id '"+ identity.userId()+"'");
        this.identity = identity;
    }
}
