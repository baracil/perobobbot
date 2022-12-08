package perobobbot.command.api;

import lombok.NonNull;
import lombok.Value;
import perobobbot.api.bus.Event;

@Value
public class CommandEvent implements Event {

    @NonNull CommandContext context;
    @NonNull CommandBinding commandBinding;



}
