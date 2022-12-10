package perobobbot.command.api;

import lombok.NonNull;
import lombok.Value;
import perobobbot.api.Event;

@Value
public class CommandEvent implements Event {

    @NonNull CommandContext context;
    @NonNull CommandBinding commandBinding;



}
