package perobobbot.twitch.eventsub.sync;

import jakarta.inject.Singleton;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import perobobbot.api.PlatformSubscriptionManager;
import perobobbot.api.data.Platform;
import perobobbot.twitch.api.Twitch;
import perobobbot.twitch.api.eventsub.TwitchEventSubConfiguration;

@Singleton
@RequiredArgsConstructor
@Slf4j
public class TwitchSubscriptionManager implements PlatformSubscriptionManager {

    private final @NonNull TwitchEventSubConfiguration configuration;

    @Override
    public @NonNull String getCallbackUrl() {
        return configuration.getCallbackUrl();
    }

    @Override
    public @NonNull Platform getPlatform() {
        return Twitch.PLATFORM;
    }


}
