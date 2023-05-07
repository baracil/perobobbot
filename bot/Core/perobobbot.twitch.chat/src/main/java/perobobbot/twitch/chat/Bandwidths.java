package perobobbot.twitch.chat;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Refill;

import java.time.Duration;
import java.util.List;

public class Bandwidths {

    private static List<Bandwidth> create(int capacity, Duration duration) {
        return List.of(
                createDirect(capacity,duration),
                createSplitToMilli(capacity,duration)
        );
    }

    private static Bandwidth createDirect(int capacity,Duration duration) {
        return Bandwidth.classic(capacity,Refill.intervally(capacity,duration));
    }

    private static Bandwidth createSplitToMilli(int capacity,Duration duration) {
        final double perMillisecond = capacity/(double)duration.toMillis();
        return createDirect((int)Math.ceil(perMillisecond), Duration.ofMillis(1));
    }



    private static final Duration _30_SECONDS = Duration.ofSeconds(30);

    public static final List<Bandwidth> _20_per_30_seconds = create(20, _30_SECONDS);
    public static final List<Bandwidth> _100_per_30_seconds = create(100, _30_SECONDS);
    public static final List<Bandwidth> _50_per_30_seconds = create(50, _30_SECONDS);
    public static final List<Bandwidth> _7500_per_30_seconds = create(7500, _30_SECONDS);



}
