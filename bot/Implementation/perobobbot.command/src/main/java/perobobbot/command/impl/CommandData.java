package perobobbot.command.impl;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import perobobbot.api.Event;
import perobobbot.command.api.CommandContext;
import perobobbot.command.api.CommandEvent;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@RequiredArgsConstructor
@EqualsAndHashCode(of = "id")
@Slf4j
public class CommandData implements Comparable<CommandData> {

    private static final AtomicLong ID = new AtomicLong(0);

    @Getter
    private final long id = ID.incrementAndGet();
    private final @NonNull Command command;

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

    public String getCommandName() {
        return command.getName();
    }

    public @NonNull Optional<? extends Event> handle(@NonNull CommandContext context, @NonNull String message) {
        final var bind = command.bind(message);

        return bind.map(binding -> new CommandEvent(context, binding));
    }

}
