package net.femtoparsec.perobobbot.oauth;

import com.google.common.collect.*;
import fpc.tools.lang.Subscription;
import lombok.NonNull;
import lombok.Synchronized;
import perobobbot.api.data.Platform;
import perobobbot.api.data.UnmanagedPlatform;
import perobobbot.api.oauth.PlatformOAuth;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

public class PlatformOAuths {

    private final @NonNull ImmutableMap<Platform, PlatformOAuth> defaultPlatforms;

    private final @NonNull BiMap<UUID, Platform> identifier = HashBiMap.create();
    private final @NonNull Map<UUID, PlatformOAuth> platformOAuths = new HashMap<>();


    public PlatformOAuths(@NonNull ImmutableList<PlatformOAuth> platformOAuths) {
        this.defaultPlatforms = platformOAuths.stream().collect(ImmutableMap.toImmutableMap(PlatformOAuth::platform, p -> p));
    }

    @Synchronized
    public @NonNull ImmutableSet<Platform> getPlatforms() {
        return Stream.concat(
                             defaultPlatforms.keySet().stream(),
                             identifier.values().stream()
                     )
                     .distinct()
                     .collect(ImmutableSet.toImmutableSet());
    }

    @Synchronized
    public @NonNull Subscription add(@NonNull PlatformOAuth platformOAuth) {
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

    public @NonNull PlatformOAuth get(@NonNull Platform platform) {
        return find(platform).orElseThrow(() -> new UnmanagedPlatform(platform));
    }

    public @NonNull Optional<PlatformOAuth> find(@NonNull Platform platform) {
        return Optional.ofNullable(identifier.inverse().get(platform))
                       .map(platformOAuths::get)
                       .or(() -> Optional.ofNullable(defaultPlatforms.get(platform)));

    }

    @Synchronized
    private void remove(@NonNull UUID uuid) {
        identifier.remove(uuid);
        platformOAuths.remove(uuid);
    }

}
