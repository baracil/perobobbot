package perobobbot.oauth.impl;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import perobobbot.oauth.api.OAuthManager;
import perobobbot.oauth.api.OAuthManagerFactory;
import perobobbot.oauth.api.PlatformOAuth;
import perobobbot.service.api.ApplicationService;

public class DefaultOAuthManagerFactory implements OAuthManagerFactory {

    @Override
    public @NonNull OAuthManager createManager(@NonNull ApplicationService applicationService, @NonNull ImmutableList<PlatformOAuth> platformOAuths) {
        return new DefaultOAuthManager(applicationService, platformOAuths);
    }
}
