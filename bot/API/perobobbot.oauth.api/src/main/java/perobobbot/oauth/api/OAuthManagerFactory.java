package perobobbot.oauth.api;

import lombok.NonNull;
import perobobbot.service.api.ApplicationService;

import java.util.List;

public interface OAuthManagerFactory {

    @NonNull OAuthManager createManager(@NonNull ApplicationService applicationService, @NonNull List<PlatformOAuth> platformOAuths);
}
