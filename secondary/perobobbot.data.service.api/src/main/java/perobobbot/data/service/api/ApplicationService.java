package perobobbot.data.service.api;

import lombok.NonNull;
import perobobbot.data.io.ApplicationForPlatformMissing;
import perobobbot.data.io.ApplicationTokenDoesNotExist;
import perobobbot.data.io.CreateApplicationParameter;
import perobobbot.data.io.CreateApplicationTokenParameter;
import perobobbot.data.io.view.ApplicationTokenView;
import perobobbot.data.io.view.ApplicationView;

import java.util.Optional;

public interface ApplicationService {

    /**
     * @param platform the name of a platform
     * @return an optional containing the application of the platform with the provided name, an empty optional if none exists
     */
    @NonNull Optional<ApplicationView> findApplication(@NonNull String platform);

    default @NonNull ApplicationView getApplication(@NonNull String platform) {
        return findApplication(platform).orElseThrow(() -> new ApplicationForPlatformMissing(platform));
    }


    @NonNull Optional<ApplicationTokenView> findApplicationToken(@NonNull String platform);

    default @NonNull ApplicationTokenView getApplicationToken(@NonNull String platform) {
        return findApplicationToken(platform).orElseThrow(() -> new ApplicationTokenDoesNotExist(platform));
    }

    @NonNull ApplicationView saveApplication(@NonNull String platform, @NonNull CreateApplicationParameter parameter);
    @NonNull ApplicationTokenView saveApplicationToken(@NonNull String platform, @NonNull CreateApplicationTokenParameter parameter);




}
