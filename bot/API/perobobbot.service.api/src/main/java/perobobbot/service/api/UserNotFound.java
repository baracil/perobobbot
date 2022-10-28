package perobobbot.service.api;

import lombok.Getter;
import lombok.NonNull;
import perobobbot.api.Identification;

@Getter
public class UserNotFound extends DomainException {

    private final @NonNull Identification identification;

    public UserNotFound(@NonNull Identification identification) {
        super("Could not find any user on platform '"+identification.platform()+"' with user Id '"+identification.userId()+"'");
        this.identification = identification;
    }
}
