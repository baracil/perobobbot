package perobobbot.api.event;

import io.micronaut.core.annotation.Nullable;
import perobobbot.api.Event;
import perobobbot.api.data.Platform;


public final class PlatformSync implements Event {

    public static PlatformSync forPlatform(Platform platform) {
        return new PlatformSync(platform);
    }

    public static PlatformSync forAllPlatforms() {
        return new PlatformSync(null);
    }

    private final @Nullable Platform platform;

    private PlatformSync(@Nullable Platform platform) {
        this.platform = platform;
    }

    public boolean appliesTo(Platform platform) {
        return this.platform == null || this.platform.equals(platform);
    }

    @Override
    public String toString() {
        if (platform == null) {
            return "PlatformSync{all platform}";
        } else {
            return "PlatformSync{platform=" + platform + '}';
        }
    }
}
