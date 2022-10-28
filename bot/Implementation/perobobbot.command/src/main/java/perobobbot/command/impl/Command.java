package perobobbot.command.impl;

import com.google.common.collect.ImmutableSet;
import fpc.tools.fp.Tuple2;
import fpc.tools.lang.MapTool;
import lombok.NonNull;
import lombok.Value;
import perobobbot.command.api.CommandBinding;
import perobobbot.command.api.Parameter;

import java.util.Optional;
import java.util.regex.Pattern;

@Value
public class Command {

    @NonNull String definition;
    @NonNull Pattern regexp;
    @NonNull String name;
    @NonNull String fullCommand;

    int minNumberOfParameters;
    int maxNumberOfParameters;

    @NonNull ImmutableSet<Parameter> parameters;

    public Command(
            @NonNull String definition,
            @NonNull String regexp,
            @NonNull String name,
            @NonNull String fullCommand,
            @NonNull ImmutableSet<Parameter> parameters) {
        this.definition = definition;
        this.regexp = Pattern.compile(regexp);
        this.name = name;
        this.fullCommand = fullCommand;
        this.parameters = parameters;
        this.maxNumberOfParameters = parameters.size();
        this.minNumberOfParameters = (int) parameters.stream().filter(p -> !p.optional()).count();
    }


    public @NonNull Optional<CommandBinding> bind(@NonNull String command) {

        if (!command.startsWith(name)) {
            return Optional.empty();
        }

        final var matcher = this.regexp.matcher(command);
        if (!matcher.matches()) {
            return Optional.empty();
        }

        final String fullParameters;
        if (matcher.groupCount() >= 1) {
            fullParameters = matcher.group(1).trim();
        } else {
            fullParameters = "";
        }


        final var parameters = getParameters()
                .stream()
                .map(Parameter::name)
                .map(n -> Optional.ofNullable(matcher.group(n)).map(v -> new Tuple2<>(n, v)))
                .flatMap(Optional::stream)
                .collect(MapTool.tuple2Collector());
        return Optional.of(new SimpleCommandBinding(definition, fullParameters, parameters));

    }
}
