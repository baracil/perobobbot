package perobobbot.api.bus;

import lombok.NonNull;
import perobobbot.api.data.Platform;


public final class PlatformSync implements Event {

    public static @NonNull PlatformSync forPlatform(@NonNull Platform platform) {
        return new PlatformSync(platform);
    }

    public static @NonNull PlatformSync forAllPlatform() {
        return new PlatformSync(null);
    }

    private final Platform platform;

    private PlatformSync(Platform platform) {
        this.platform = platform;
    }

    public boolean appliesTo(@NonNull Platform platform) {
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
