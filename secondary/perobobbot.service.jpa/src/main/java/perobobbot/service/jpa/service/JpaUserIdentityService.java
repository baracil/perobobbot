package perobobbot.service.jpa.service;

import jakarta.inject.Singleton;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.api.data.Platform;
import perobobbot.api.data.view.UserIdentity;
import perobobbot.service.api.UserIdentityService;
import perobobbot.service.jpa.repository.UserIdentityRepository;

@RequiredArgsConstructor
@Singleton
public class JpaUserIdentityService implements UserIdentityService {

    private final @NonNull UserIdentityRepository userIdentityRepository;

    @Override
    public @NonNull UserIdentity getByPlatformAndLogin(@NonNull Platform platform, @NonNull String login) {
        return userIdentityRepository.getByPlatformAndLogin(platform,login).toView();
    }
}
