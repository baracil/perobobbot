package perobobbot.twitch.chat.impl;

import com.google.common.collect.Sets;
import fpc.tools.advanced.chat.AdvancedIO;
import fpc.tools.lang.LoopAction;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.api.ChannelProviderForUser;
import perobobbot.api.data.Channel;
import perobobbot.twitch.chat.impl._private.ChatAction;
import perobobbot.twitch.chat.impl._private.JoinAction;
import perobobbot.twitch.chat.impl._private.PartAction;
import perobobbot.twitch.chat.message.from.MessageFromTwitch;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class ChannelJoiner implements LoopAction {

    private final ChannelProviderForUser channelProvider;
    private final AdvancedIO<MessageFromTwitch> io;

    private final Set<Channel> joined = Collections.synchronizedSet(new HashSet<>());

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
                toPart.stream().map(c -> new PartAction(joined, c)),
                toJoin.stream().map(c -> new JoinAction(joined, c))
        ).reduce(ChatAction::accumulate).orElse(ChatAction.NOP);

        action.execute(io).toCompletableFuture().get();

        return NextState.CONTINUE;
    }

    @Override
    public boolean shouldStopOnError(@NonNull Throwable error) {
        return false;
    }
}
