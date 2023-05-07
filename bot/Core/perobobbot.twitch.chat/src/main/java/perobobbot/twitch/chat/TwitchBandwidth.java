package perobobbot.twitch.chat;

import fpc.tools.chat.BandwidthLimits;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.ConfigurationBuilder;
import io.github.bucket4j.local.LocalBucketBuilder;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public enum TwitchBandwidth implements BandwidthLimits {
    REGULAR(Bandwidths._20_per_30_seconds),
    MODERATOR(Bandwidths._100_per_30_seconds),
    KNOWN_BOT(Bandwidths._50_per_30_seconds),
    VERIFIED_BOT(Bandwidths._7500_per_30_seconds),
    ;

    private final List<Bandwidth> bandwidth;

    TwitchBandwidth(Bandwidth... bandwidth) {
        this.bandwidth = List.of(bandwidth);
    }

    @Override
    public LocalBucketBuilder addLimits(LocalBucketBuilder builder) {
        bandwidth.forEach(builder::addLimit);
        return builder;
    }

    public ConfigurationBuilder addLimits(ConfigurationBuilder builder) {
        bandwidth.forEach(builder::addLimit);
        return builder;
    }


}
