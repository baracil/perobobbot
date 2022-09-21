package perobobbot.service.api;

import lombok.NonNull;
import perobobbot.api.data.*;
import perobobbot.api.data.view.ApplicationToken;
import perobobbot.api.data.view.Application;

import java.util.Optional;

public interface ApplicationService {

    /**
     * @param platform the platform
     * @return an optional containing the application of the platform with the provided name, an empty optional if none exists
     */
    @NonNull Optional<Application> findApplication(@NonNull Platform platform);

    /**
     * @param platform the platform to look for
     * @return the application for the given platform
     * @throws ApplicationForPlatformMissing if no application exists for the given platform
     */
    default @NonNull Application getApplication(@NonNull Platform platform) {
        return findApplication(platform).orElseThrow(() -> new ApplicationForPlatformMissing(platform));
    }

    @NonNull String getApplicationClientId(@NonNull Platform platform);

    /**
     * @param platform the platform
     * @return an optional containing the application token for the given platform if it exists, an empty optional otherwise
     */
    @NonNull Optional<ApplicationToken> findApplicationToken(@NonNull Platform platform);

    /**
     * @param platform the platform
     * @return the application token for the given platform if it exists
     * @throws ApplicationForPlatformMissing if no application exists for the given platform
     * @throws ApplicationTokenDoesNotExist if no token exists for the given platform
     */
    default @NonNull ApplicationToken getApplicationToken(@NonNull Platform platform) {
        return findApplicationToken(platform).orElseThrow(() -> new ApplicationTokenDoesNotExist(platform));
    }

    /**
     * Create an application for the given platform and save or replace any existing.
     * @param platform the platform
     * @param parameter the parameter to create the application
     * @return the newly created application
     */
    @NonNull Application saveApplication(@NonNull Platform platform, @NonNull CreateApplicationParameter parameter);

    /**
     * @param platform the platform
     * @param parameter the parameter to create the token
     * @return the newly created token
     */
    @NonNull ApplicationToken saveApplicationToken(@NonNull Platform platform, @NonNull CreateApplicationTokenParameter parameter);




}
