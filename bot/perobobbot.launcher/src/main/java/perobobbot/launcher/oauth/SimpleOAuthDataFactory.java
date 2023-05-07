package perobobbot.launcher.oauth;

import fpc.tools.lang.Instants;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import perobobbot.api.UserInfo;
import perobobbot.oauth.api.OAuthData;
import perobobbot.oauth.api.OAuthDataFactory;
import perobobbot.oauth.api.OAuthManager;
import perobobbot.service.api.ApplicationService;
import perobobbot.service.api.UserTokenService;

@RequiredArgsConstructor
@Singleton
public class SimpleOAuthDataFactory implements OAuthDataFactory {

    private final UserTokenService userTokenService;
    private final ApplicationService applicationService;
    private final OAuthManager oAuthManager;
    private final Instants instants;

    @Override
    public OAuthData create(UserInfo userInfo) {
        return new SimpleOAuthData(userInfo, userTokenService, applicationService, oAuthManager, instants);
    }
}
