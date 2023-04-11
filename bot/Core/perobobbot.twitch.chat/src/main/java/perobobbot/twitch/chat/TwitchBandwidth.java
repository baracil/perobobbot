package perobobbot.twitch.chat;

import fpc.tools.chat.BandwidthLimits;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.ConfigurationBuilder;
import io.github.bucket4j.local.LocalBucketBuilder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public enum TwitchBandwidth implements BandwidthLimits {
    REGULAR(Bandwidths._20_per_30_seconds),
    MODERATOR(Bandwidths._100_per_30_seconds),
    KNOWN_BOT(Bandwidths._50_per_30_seconds),
    VERIFIED_BOT(Bandwidths._7500_per_30_seconds),
    ;

    @NonNull
    private final List<Bandwidth> bandwidth;

    TwitchBandwidth(@NonNull Bandwidth... bandwidth) {
        this.bandwidth = List.of(bandwidth);
    }

    @Override
    public @NonNull LocalBucketBuilder addLimits(@NonNull LocalBucketBuilder builder) {
        bandwidth.forEach(builder::addLimit);
        return builder;
    }

    @NonNull
    public ConfigurationBuilder addLimits(@NonNull ConfigurationBuilder builder) {
        bandwidth.forEach(builder::addLimit);
        return builder;
    }


}
