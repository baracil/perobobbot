package perobobbot.oauth.api;

import perobobbot.service.api.ApplicationService;

import java.util.List;

public interface OAuthManagerFactory {

    OAuthManager createManager(ApplicationService applicationService, List<PlatformOAuth> platformOAuths);
}
