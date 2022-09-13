package perobobbot.data.service.api;

import lombok.NonNull;
import perobobbot.data.io.*;
import perobobbot.data.io.view.ApplicationTokenView;
import perobobbot.data.io.view.ApplicationView;

import java.util.Optional;

public interface ApplicationService {

    /**
     * @param platform the platform
     * @return an optional containing the application of the platform with the provided name, an empty optional if none exists
     */
    @NonNull Optional<ApplicationView> findApplication(@NonNull Platform platform);

    /**
     * @param platform the platform to look for
     * @return the application for the given platform
     * @throws ApplicationForPlatformMissing if no application exists for the given platform
     */
    default @NonNull ApplicationView getApplication(@NonNull Platform platform) {
        return findApplication(platform).orElseThrow(() -> new ApplicationForPlatformMissing(platform));
    }

    /**
     * @param platform the platform
     * @return an optional containing the application token for the given platform if it exists, an empty optional otherwise
     */
    @NonNull Optional<ApplicationTokenView> findApplicationToken(@NonNull Platform platform);

    /**
     * @param platform the platform
     * @return the application token for the given platform if it exists
     * @throws ApplicationForPlatformMissing if no application exists for the given platform
     * @throws ApplicationTokenDoesNotExist if no token exists for the given platform
     */
    default @NonNull ApplicationTokenView getApplicationToken(@NonNull Platform platform) {
        return findApplicationToken(platform).orElseThrow(() -> new ApplicationTokenDoesNotExist(platform));
    }

    /**
     * Create an application for the given platform and save or replace any existing.
     * @param platform the platform
     * @param parameter the parameter to create the application
     * @return the newly created application
     */
    @NonNull ApplicationView saveApplication(@NonNull Platform platform, @NonNull CreateApplicationParameter parameter);

    /**
     * @param platform the platform
     * @param parameter the parameter to create the token
     * @return the newly created token
     */
    @NonNull ApplicationTokenView saveApplicationToken(@NonNull Platform platform, @NonNull CreateApplicationTokenParameter parameter);




}
