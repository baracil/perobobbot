package perobobbot.oauth.api;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import perobobbot.service.api.ApplicationService;

public interface OAuthManagerFactory {

    @NonNull OAuthManager createManager(@NonNull ApplicationService applicationService, @NonNull ImmutableList<PlatformOAuth> platformOAuths);
}
