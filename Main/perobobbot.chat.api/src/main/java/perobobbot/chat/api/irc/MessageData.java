package perobobbot.chat.api.irc;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.ImmutableMap;
import fpc.tools.irc.IRCParsing;
import fpc.tools.lang.MapTool;
import io.micronaut.serde.annotation.Serdeable;
import lombok.NonNull;
import lombok.Value;

import java.util.Optional;
import java.util.stream.Stream;

@Value
@Serdeable
public class MessageData {

    @NonNull String rawMessage;
    @NonNull ImmutableMap<String,Tag> tags;
    Prefix prefix;
    @NonNull String command;
    @NonNull Params params;

    @NonNull
    public Optional<Prefix> getPrefix() {
        return Optional.ofNullable(prefix);
    }

    /**
     * @return the last parameter of the IRC message.
     * @throws IndexOutOfBoundsException if there is no such parameter
     */
    @NonNull
    @JsonIgnore
    public String getLastParameter() {
        return params.getLastParameter();
    }

    /**
     * The last parameter split in token
     * @param sep
     * @return
     */
    @NonNull
    @JsonIgnore
    public Stream<String> splitLastParameter(@NonNull String sep) {
        return Stream.of(getLastParameter().split(sep));
    }

    @NonNull
    @JsonIgnore
    public Optional<String> tagValue(@NonNull String tagName) {
        return Optional.ofNullable(tags.get(tagName)).map(Tag::getValue);
    }


    public static @NonNull MessageData fromFpc(@NonNull IRCParsing ircParsing) {
        return new MessageData(
                ircParsing.getRawMessage(),
                MapTool.mapValue(ircParsing.getTags(), Tag::fromFpc),
                ircParsing.getPrefix().map(Prefix::fromFpc).orElse(null),
                ircParsing.getCommand(),
                Params.fromFpc(ircParsing.getParams())
        );
    }
}
