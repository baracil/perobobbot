package perobobbot.oauth.impl;

import perobobbot.oauth.api.OAuthManager;
import perobobbot.oauth.api.OAuthManagerFactory;
import perobobbot.oauth.api.PlatformOAuth;
import perobobbot.service.api.ApplicationService;

import java.util.List;

public class DefaultOAuthManagerFactory implements OAuthManagerFactory {

    @Override
    public OAuthManager createManager(ApplicationService applicationService, List<PlatformOAuth> platformOAuths) {
        return new DefaultOAuthManager(applicationService, platformOAuths);
    }
}
