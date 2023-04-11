package perobobbot.service.api;

import fpc.tools.lang.Secret;
import lombok.NonNull;
import perobobbot.api.data.*;

import java.util.List;
import java.util.Optional;

public interface ApplicationService {

    int VERSION = 1;

    /**
     * @param platform the platform
     * @return an optional containing the application of the platform with the provided name, an empty optional if none exists
     */
    @NonNull Optional<Application.Decrypted> findApplication(@NonNull Platform platform);

    /**
     * @param platform the platform to look for
     * @return the application for the given platform
     * @throws ApplicationForPlatformMissing if no application exists for the given platform
     */
    default @NonNull Application.Decrypted getApplication(@NonNull Platform platform) {
        return findApplication(platform).orElseThrow(() -> new ApplicationForPlatformMissing(platform));
    }

    @NonNull String getApplicationClientId(@NonNull Platform platform);

    /**
     * @param platform the platform
     * @return an optional containing the application token for the given platform if it exists, an empty optional otherwise
     */
    @NonNull Optional<ApplicationToken.Decrypted> findApplicationToken(@NonNull Platform platform);

    /**
     * @param platform the platform
     * @return the application token for the given platform if it exists
     * @throws ApplicationForPlatformMissing if no application exists for the given platform
     * @throws ApplicationTokenDoesNotExist if no token exists for the given platform
     */
    default @NonNull ApplicationToken.Decrypted getApplicationToken(@NonNull Platform platform) {
        return findApplicationToken(platform).orElseThrow(() -> new ApplicationTokenDoesNotExist(platform));
    }

    /**
     * Create an application for the given platform and save or replace any existing.
     * @param platform the platform
     * @param parameter the parameter to create the application
     * @return the newly created application
     */
    @NonNull Application.Decrypted saveApplication(@NonNull Platform platform, @NonNull CreateApplicationParameter parameter);

    /**
     * @param applicationToken the platform
     * @return the newly created token
     */
    @NonNull ApplicationToken.Decrypted saveApplicationToken(@NonNull ApplicationToken<Secret> applicationToken);


    @NonNull
    List<Platform> getAllPlatforms();
}
