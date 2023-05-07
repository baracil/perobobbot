package perobobbot.twitch.chat.message.from;

import fpc.tools.fp.Function1;
import fpc.tools.irc.IRCParsing;
import fpc.tools.irc.Prefix;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import perobobbot.twitch.chat.TwitchChannel;
import perobobbot.twitch.chat.message.Capability;
import perobobbot.twitch.chat.message.IRCCommand;
import perobobbot.twitch.chat.message.TagKey;

import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Bastien Aracil
 **/
@RequiredArgsConstructor
public class AnswerBuilderHelper {

    @Getter
    private final IRCParsing ircParsing;

    public Optional<IRCCommand> command() {
        return IRCCommand.findFromString(ircParsing.getCommand());
    }

    public Set<Capability> capabilities() {
        return ircParsing.splitLastParameter(" ")
                         .map(Capability::find)
                         .flatMap(Optional::stream)
                         .collect(Collectors.toSet());
    }

    public String lastParameter() {
        return ircParsing.lastParameter();
    }


    public <T> T mapParameter(int parameterIndex, Function<? super String, ? extends T> mapper) {
        return mapper.apply(ircParsing.getParams().parameterAt(parameterIndex));
    }

    public TwitchChannel channelFromParameterAt(int parameterIndex) {
        return mapParameter(parameterIndex, TwitchChannel::create);
    }

    public <T> T tagValue(TagKey key, Function1<? super String, ? extends T> mapper) {
        return mapper.apply(tagValue(key));
    }

    public String tagValue(TagKey key) {
        return optionalTagValue(key)
                .orElseThrow(() -> buildException("Could not find tag with name '"+key.name()+"'"));
    }

    public Optional<String> optionalTagValue(TagKey key) {
        return ircParsing.tagValue(key.getKeyName());
    }

    public String tagValue(TagKey key, String defaultValue) {
        return optionalTagValue(key)
                .orElse(defaultValue);
    }

    public <T> Optional<T> optionalTagValue(TagKey key, Function<? super String, ? extends T> mapper) {
        return optionalTagValue(key)
                .map(mapper);
    }

    public boolean tagValueAsBoolean(TagKey key, boolean defaultValue) {
        final String value = optionalTagValue(key).orElse(null);
        return value == null?defaultValue:value.equals("1");
    }

    public int tagValueAsInt(TagKey key, int defaultValue) {
        return optionalTagValueAsInt(key).orElse(defaultValue);
    }

    public Optional<Integer> optionalTagValueAsInt(TagKey key) {
        return optionalTagValue(key, Integer::parseInt);
    }


    public String userFromPrefix() {
        return prefix().getNickOrServerName();
    }

    private Prefix prefix() {
        return ircParsing.getPrefix()
                         .orElseThrow(() -> buildException("No prefix defined"));
    }

    private IllegalArgumentException buildException(String message) {
        return new IllegalArgumentException(message+". Raw Message:'"+ircParsing.getRawMessage()+"'");
    }

    public String parameterAt(int parameterIndex) {
        return ircParsing.getParams().parameterAt(parameterIndex);
    }
}
