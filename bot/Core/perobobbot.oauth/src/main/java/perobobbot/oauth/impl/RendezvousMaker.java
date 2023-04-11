package perobobbot.oauth.impl;

import fpc.tools.lang.ThreadFactoryBuilder;
import lombok.NonNull;
import lombok.Synchronized;
import lombok.experimental.Delegate;
import perobobbot.api.data.TokenWithIdentity;
import perobobbot.oauth.api.AuthorizationCodeGranFlow;
import perobobbot.oauth.api.Failure;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class RendezvousMaker {

    private static final ScheduledExecutorService EXECUTOR_SERVICE = Executors.newScheduledThreadPool(1, new ThreadFactoryBuilder().setDaemon(true).build());

    private final Map<String, Rendezvous> rdvs = new HashMap<>();

    public RendezvousMaker() {
        EXECUTOR_SERVICE.scheduleAtFixedRate(this::pickUpRabbits, 1, 1, TimeUnit.MINUTES);
    }

    @Synchronized
    public void addFlow(@NonNull String state, @NonNull DefaultAuthorizationCodeGranFlow flow) {
        Optional.ofNullable(rdvs.put(state, new Rendezvous(flow)))
                .ifPresent(f -> f.setFailed(new Failure.TimedOut()));
    }

    @Synchronized
    public @NonNull Optional<CompletableFuture<TokenWithIdentity>> extractFlow(@NonNull String state) {
        return Optional.ofNullable(rdvs.remove(state)).map(Rendezvous::flow).map(DefaultAuthorizationCodeGranFlow::getFuture);
    }

    @Synchronized
    private void pickUpRabbits() {
        final var now = System.nanoTime();
        final var iterator = rdvs.values().iterator();
        while (iterator.hasNext()) {
            final var rdv = iterator.next();
            if ((rdv.timeOfArrivalOfTheRabbit - now) < 0) {
                iterator.remove();
                rdv.setFailed(new Failure.TimedOut());
            }
        }
    }

    private record Rendezvous(@Delegate @NonNull DefaultAuthorizationCodeGranFlow flow,
                              long timeOfArrivalOfTheRabbit) implements AuthorizationCodeGranFlow {
        private Rendezvous(@NonNull DefaultAuthorizationCodeGranFlow flow) {
            this(flow, System.nanoTime() + Duration.ofMinutes(1).toNanos());
        }
    }


}
