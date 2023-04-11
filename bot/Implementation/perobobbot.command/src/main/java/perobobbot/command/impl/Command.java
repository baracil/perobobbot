package perobobbot.command.impl;

import fpc.tools.fp.Tuple2;
import fpc.tools.lang.MapTool;
import io.micronaut.core.annotation.Nullable;
import lombok.NonNull;
import lombok.Value;
import perobobbot.command.api.CommandBinding;
import perobobbot.command.api.Parameter;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Value
public class Command {

    @NonNull String definition;
    @NonNull Pattern regexp;
    @NonNull String name;
    @NonNull String fullCommand;

    int minNumberOfParameters;
    int maxNumberOfParameters;

    @NonNull Set<Parameter> parameters;

    public Command(
            @NonNull String definition,
            @NonNull String regexp,
            @NonNull String name,
            @NonNull String fullCommand,
            @NonNull Set<Parameter> parameters) {
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
                .map(n -> extractParameterValue(matcher, n))
                .filter(Objects::nonNull)
                .collect(MapTool.tuple2Collector());
        return Optional.of(new CommandBinding(definition, fullParameters, parameters));

    }

    private @Nullable Tuple2<String, String> extractParameterValue(@NonNull Matcher matcher, @NonNull String parameterName) {
        final var match = matcher.group(parameterName);
        if (match == null) {
            return null;
        }

        final var unquoted = unquote(unquote(match, "'"), "\"");
        return new Tuple2<>(parameterName, unquoted);
    }

    private @NonNull String unquote(@NonNull String value, @NonNull String quoteType) {
        if (value.startsWith(quoteType) && value.endsWith(quoteType)) {
            return value.substring(1, value.length() - 1);
        }
        return value;
    }
}
