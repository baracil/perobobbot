package perobobbot.twitch.api;

import com.google.common.collect.ImmutableSet;
import perobobbot.api.data.Platform;

public class Twitch {
    public static final Platform PLATFORM = new Platform("twitch");
    public static final ImmutableSet<TwitchScope> SCOPES = ImmutableSet.copyOf(
            TwitchScope.values()
    );

    public static final String HELIX_URL = "https://api.twitch.tv/helix";
}
