package perobobbot.launcher;

import com.google.common.collect.ImmutableList;
import jakarta.inject.Singleton;
import lombok.NonNull;
import perobobbot.api.oauth.PlatformOAuth;
import perobobbot.api.plugin.PerobobbotService;
import perobobbot.oauth.OAuthManager;
import perobobbot.service.api.ApplicationService;

import java.util.List;

@io.micronaut.context.annotation.Factory
public class Factory {

    @Singleton
    @PerobobbotService(serviceType = OAuthManager.class, apiVersion = OAuthManager.VERSION)
    public @NonNull OAuthManager oAuthManager(@NonNull ApplicationService applicationService, @NonNull List<PlatformOAuth> platformOAuths) {
        return OAuthManager.create(applicationService, ImmutableList.copyOf(platformOAuths));
    }
}
