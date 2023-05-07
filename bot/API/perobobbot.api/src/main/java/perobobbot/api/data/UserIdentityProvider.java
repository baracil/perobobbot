package perobobbot.api.data;

import perobobbot.api.Identity;

public interface UserIdentityProvider {

    UserIdentity getUserIdentity(Identity identity);
}
