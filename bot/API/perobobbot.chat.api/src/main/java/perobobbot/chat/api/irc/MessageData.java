package perobobbot.chat.api.irc;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fpc.tools.irc.IRCParsing;
import fpc.tools.lang.MapTool;
import jakarta.annotation.Nullable;
import lombok.Value;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

@Value
public class MessageData {

    String rawMessage;
    Map<String,Tag> tags;
    @Nullable Prefix prefix;
    String command;
    Params params;

    public Optional<Prefix> getPrefix() {
        return Optional.ofNullable(prefix);
    }

    /**
     * @return the last parameter of the IRC message.
     * @throws IndexOutOfBoundsException if there is no such parameter
     */
    @JsonIgnore
    public String getLastParameter() {
        return params.getLastParameter();
    }

    /**
     * The last parameter split in token
     * @param sep
     * @return
     */
    @JsonIgnore
    public Stream<String> splitLastParameter(String sep) {
        return Stream.of(getLastParameter().split(sep));
    }

    @JsonIgnore
    public Optional<String> tagValue(String tagName) {
        return Optional.ofNullable(tags.get(tagName)).map(Tag::getValue);
    }


    public static MessageData fromFpc(IRCParsing ircParsing) {
        return new MessageData(
                ircParsing.getRawMessage(),
                MapTool.mapValue(ircParsing.getTags(), Tag::fromFpc),
                ircParsing.getPrefix().map(Prefix::fromFpc).orElse(null),
                ircParsing.getCommand(),
                Params.fromFpc(ircParsing.getParams())
        );
    }

}
