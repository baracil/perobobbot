package perobobbot.command._private;

import fpc.tools.fp.Consumer1;
import fpc.tools.lang.ThrowableTool;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import perobobbot.command.CommandBinding;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

@RequiredArgsConstructor
@EqualsAndHashCode(of = "id")
@Slf4j
public class CommandData implements Comparable<CommandData> {

    private static final ExecutorService COMMAND_EXECUTOR = Executors.newSingleThreadExecutor();

    private static final AtomicLong ID = new AtomicLong(0);

    @Getter
    private final long id = ID.incrementAndGet();
    private final @NonNull Command command;
    private final @NonNull Consumer1<? super CommandBinding> execution;

    @Override
    public int compareTo(CommandData o) {
        final var n1 = this.command.getFullCommand();
        final var n2 = o.command.getFullCommand();

        final var common = Math.min(n1.length(), n2.length());
        final var cn1 = n1.substring(0, common);
        final var cn2 = n2.substring(0, common);

        final var diff = cn1.compareTo(cn2);
        if (diff == 0) {
            //longest first
            final var d = n2.length() - n1.length();
            if (d == 0) {
                return Long.compare(this.id, o.id);
            }
        }
        return diff;
    }

    ;

    public String getCommandName() {
        return command.getName();
    }

    public boolean handle(String message) {
        final var bind = command.bind(message);
        bind.map(b -> new Executor(b, this.execution))
            .ifPresent(COMMAND_EXECUTOR::submit);
        return bind.isPresent();
    }

    @RequiredArgsConstructor
    private static class Executor implements Runnable {
        private final @NonNull CommandBinding commandBinding;
        private final @NonNull Consumer1<? super CommandBinding> execution;

        @Override
        public void run() {
            try {
                execution.accept(commandBinding);
            } catch (Throwable t) {
                ThrowableTool.interruptIfCausedByInterruption(t);
                LOG.warn("Error while execution action.", t);
            }
        }
    }
}
