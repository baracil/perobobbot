package perobobbot.service.api;

import fpc.tools.lang.Secret;
import perobobbot.api.data.*;

import java.util.List;
import java.util.Optional;

public interface ApplicationService {

    int VERSION = 1;

    /**
     * @param platform the platform
     * @return an optional containing the application of the platform with the provided name, an empty optional if none exists
     */
    Optional<Application.Decrypted> findApplication(Platform platform);

    /**
     * @param platform the platform to look for
     * @return the application for the given platform
     * @throws ApplicationForPlatformMissing if no application exists for the given platform
     */
    default Application.Decrypted getApplication(Platform platform) {
        return findApplication(platform).orElseThrow(() -> new ApplicationForPlatformMissing(platform));
    }

    String getApplicationClientId(Platform platform);

    /**
     * @param platform the platform
     * @return an optional containing the application token for the given platform if it exists, an empty optional otherwise
     */
    Optional<ApplicationToken.Decrypted> findApplicationToken(Platform platform);

    /**
     * @param platform the platform
     * @return the application token for the given platform if it exists
     * @throws ApplicationForPlatformMissing if no application exists for the given platform
     * @throws ApplicationTokenDoesNotExist if no token exists for the given platform
     */
    default ApplicationToken.Decrypted getApplicationToken(Platform platform) {
        return findApplicationToken(platform).orElseThrow(() -> new ApplicationTokenDoesNotExist(platform));
    }

    /**
     * Create an application for the given platform and save or replace any existing.
     * @param platform the platform
     * @param parameter the parameter to create the application
     * @return the newly created application
     */
    Application.Decrypted saveApplication(Platform platform, CreateApplicationParameter parameter);

    /**
     * @param applicationToken the platform
     * @return the newly created token
     */
    ApplicationToken.Decrypted saveApplicationToken(ApplicationToken<Secret> applicationToken);


    List<Platform> getAllPlatforms();
}
