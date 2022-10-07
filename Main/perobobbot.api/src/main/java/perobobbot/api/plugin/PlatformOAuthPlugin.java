package perobobbot.api.plugin;

import lombok.NonNull;
import perobobbot.api.oauth.PlatformOAuth;

public record PlatformOAuthPlugin(@NonNull PlatformOAuth platformOAuth) implements PerobobbotPluginData {
}
