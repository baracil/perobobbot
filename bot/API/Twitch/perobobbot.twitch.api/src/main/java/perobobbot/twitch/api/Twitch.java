package perobobbot.twitch.api;

import perobobbot.api.data.Platform;

import java.util.Set;

public class Twitch {
    public static final Platform PLATFORM = new Platform("twitch");

    public static final Set<TwitchScope> SCOPES = Set.of(TwitchScope.values());

    public static final String HELIX_URL = "https://api.twitch.tv/helix";
}
