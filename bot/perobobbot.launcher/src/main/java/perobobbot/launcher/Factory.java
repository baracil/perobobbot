package perobobbot.launcher;

import com.google.common.collect.ImmutableList;
import jakarta.inject.Singleton;
import lombok.NonNull;
import perobobbot.api.plugin.PerobobbotService;
import perobobbot.oauth.api.OAuthManager;
import perobobbot.oauth.api.OAuthManagerFactory;
import perobobbot.oauth.api.PlatformOAuth;
import perobobbot.service.api.ApplicationService;

import java.util.List;
import java.util.ServiceLoader;

@io.micronaut.context.annotation.Factory
public class Factory {

    @Singleton
    @PerobobbotService(serviceType = OAuthManager.class, apiVersion = OAuthManager.VERSION)
    public @NonNull OAuthManager oAuthManager(@NonNull ApplicationService applicationService, @NonNull List<PlatformOAuth> platformOAuths) {
        return ServiceLoader.load(OAuthManagerFactory.class)
                            .stream()
                            .findFirst()
                            .orElseThrow(() -> new IllegalArgumentException("Could not find any '" + OAuthManagerFactory.class + "'"))
                            .get().createManager(applicationService, ImmutableList.copyOf(platformOAuths));
    }
}
