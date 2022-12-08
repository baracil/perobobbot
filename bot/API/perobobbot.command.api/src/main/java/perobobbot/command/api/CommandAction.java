package perobobbot.command.api;

import lombok.NonNull;

public interface CommandAction {

    void execute(@NonNull CommandContext context, @NonNull CommandBinding commandBinding);
}
