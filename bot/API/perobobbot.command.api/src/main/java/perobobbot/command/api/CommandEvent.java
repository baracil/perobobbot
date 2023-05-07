package perobobbot.command.api;

import lombok.Value;
import perobobbot.api.Event;

@Value
public class CommandEvent implements Event {

    CommandContext context;
    CommandBinding commandBinding;



}
