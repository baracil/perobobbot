package perobobobbot.command;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Builder
@Getter
public class Parsing {

    private final String fullCommand;
    private final String regexp;
    private final int nbParameters;

}
