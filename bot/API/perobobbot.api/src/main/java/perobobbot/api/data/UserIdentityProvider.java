package perobobbot.api.data;

import lombok.NonNull;
import perobobbot.api.Identity;

public interface UserIdentityProvider {

    @NonNull UserIdentity getUserIdentity(@NonNull Identity identity);
}
