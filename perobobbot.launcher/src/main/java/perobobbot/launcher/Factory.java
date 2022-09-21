package perobobbot.launcher;

import com.google.common.collect.ImmutableList;
import jakarta.inject.Singleton;
import lombok.NonNull;
import perobobbot.api.oauth.PlatformOAuth;
import perobobbot.oauth.OAuthManager;
import perobobbot.service.api.ApplicationService;

import java.util.List;

@io.micronaut.context.annotation.Factory
public class Factory {

    @Singleton
    public @NonNull OAuthManager oAuthManager(@NonNull ApplicationService applicationService, @NonNull List<PlatformOAuth> platformOAuths) {
        return OAuthManager.create(ImmutableList.copyOf(platformOAuths),applicationService);
    }
}
