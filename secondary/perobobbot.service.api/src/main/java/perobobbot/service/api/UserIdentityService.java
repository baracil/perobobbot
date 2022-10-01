package perobobbot.service.api;

import lombok.NonNull;
import perobobbot.api.data.Platform;
import perobobbot.api.data.view.UserIdentity;

public interface UserIdentityService {

    @NonNull UserIdentity getByPlatformAndLogin(@NonNull Platform platform, @NonNull String login);
}
