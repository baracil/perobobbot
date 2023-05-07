package perobobbot.oauth.impl;

import fpc.tools.lang.BiMap;
import fpc.tools.lang.Subscription;
import lombok.Synchronized;
import perobobbot.api.data.Platform;
import perobobbot.api.data.UnmanagedPlatform;
import perobobbot.oauth.api.PlatformOAuth;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PlatformOAuths {

    private final Map<Platform, PlatformOAuth> defaultPlatforms;

    private final BiMap<UUID, Platform> identifier = BiMap.createHashBiMap();
    private final Map<UUID, PlatformOAuth> platformOAuths = new HashMap<>();


    public PlatformOAuths(List<PlatformOAuth> platformOAuths) {
        this.defaultPlatforms = platformOAuths.stream().collect(Collectors.toMap(PlatformOAuth::platform, Function.identity()));
    }

    @Synchronized
    public Set<Platform> getPlatforms() {
        return Stream.concat(
                             defaultPlatforms.keySet().stream(),
                             identifier.values().stream()
                     )
                     .collect(Collectors.toSet());
    }

    @Synchronized
    public Subscription add(PlatformOAuth platformOAuth) {
        final var platform = platformOAuth.platform();

        final var existingId = identifier.inverse().get(platform);
        if (existingId != null) {
            remove(existingId);
        }
        final var id = UUID.randomUUID();
        identifier.put(id, platform);
        platformOAuths.put(id, platformOAuth);

        return () -> remove(id);
    }

    public PlatformOAuth get(Platform platform) {
        return find(platform).orElseThrow(() -> new UnmanagedPlatform(platform));
    }

    public Optional<PlatformOAuth> find(Platform platform) {
        return Optional.ofNullable(identifier.inverse().get(platform))
                       .map(platformOAuths::get)
                       .or(() -> Optional.ofNullable(defaultPlatforms.get(platform)));

    }

    @Synchronized
    private void remove(UUID uuid) {
        identifier.remove(uuid);
        platformOAuths.remove(uuid);
    }

}
