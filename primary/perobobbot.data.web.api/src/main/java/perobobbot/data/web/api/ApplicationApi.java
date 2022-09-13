package perobobbot.data.web.api;

import io.micronaut.http.annotation.*;
import lombok.NonNull;
import perobobbot.data.io.CreateApplicationParameter;
import perobobbot.data.io.CreateApplicationTokenParameter;
import perobobbot.data.io.Platform;
import perobobbot.data.io.view.ApplicationTokenView;
import perobobbot.data.io.view.ApplicationView;
import perobobbot.data.io.view.EncryptedApplicationView;

import java.util.Optional;

public interface ApplicationApi extends WebService {

    String PATH = ROOT_PATH+"/application";

    /**
     * Retrieve the application for the given platform if any exists
     *
     * @param platform the name of the platform
     * @return an optional containing the application
     */
    @Get("/{platform}")
    @NonNull ApplicationView getApplication(@NonNull @PathVariable Platform platform);

    @Get("/{platform}/clientId")
    @NonNull String getApplicationClientId(@NonNull Platform platform);

    @Get("/{platform}{?publicKey")
    @NonNull EncryptedApplicationView getApplicationEncrypted(@NonNull @PathVariable Platform platform, @QueryValue String base64EncodePublicKey);

    @Put("/{platform}")
    @NonNull ApplicationView createApplication(@NonNull @PathVariable Platform platform, @NonNull @Body CreateApplicationParameter parameter);

    @Get("/{platform}/token")
    @NonNull Optional<ApplicationTokenView> findApplicationTokenView(@NonNull @PathVariable Platform platform);

    @Put("/{platform}/token")
    @NonNull ApplicationTokenView createApplicationTokenView(@NonNull @PathVariable Platform platform, @NonNull @Body CreateApplicationTokenParameter parameters);
}
