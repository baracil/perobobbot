package perobobbot.twitch.chat.impl._private;

import com.google.common.collect.Sets;
import fpc.tools.advanced.chat.AdvancedIO;
import fpc.tools.lang.LoopAction;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.api.JoinedChannelProviderForUser;
import perobobbot.api.data.JoinedChannel;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Loop action that check with DB channel that has been joined and parted
 * and update connection accordingly
 */
@RequiredArgsConstructor
public class ChannelJoiner implements LoopAction {

    private final @NonNull String userId;
    private final @NonNull JoinedChannelProviderForUser channelProvider;
    private final @NonNull AdvancedIO io;

    private final Set<JoinedChannel> joined = Collections.synchronizedSet(new HashSet<>());

    @Override
    public @NonNull NextState beforeLooping() {
        joined.clear();
        return NextState.CONTINUE;
    }

    @Override
    public @NonNull NextState performOneIteration() throws Throwable {
        final var fromDb = channelProvider.getChannels();
        final var toPart = Sets.difference(joined, fromDb);
        final var toJoin = Sets.difference(fromDb, joined);

        final var action = Stream.concat(
                toPart.stream().map(c -> new PartAction(userId,joined, c)),
                toJoin.stream().map(c -> new JoinAction(userId, joined, c))
        ).reduce(ChatAction::accumulate).orElse(ChatAction.NOP);

        action.execute(io).toCompletableFuture().get();

        return NextState.CONTINUE;
    }

    @Override
    public boolean shouldStopOnError(@NonNull Throwable error) {
        return false;
    }
}
