package perobobbot.twitch.chat.message.from;

import fpc.tools.fp.Function1;
import fpc.tools.irc.IRCParsing;
import fpc.tools.irc.Prefix;
import lombok.Getter;
import lombok.NonNull;
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

    @NonNull
    @Getter
    private final IRCParsing ircParsing;

    @NonNull
    public Optional<IRCCommand> command() {
        return IRCCommand.findFromString(ircParsing.getCommand());
    }

    @NonNull
    public Set<Capability> capabilities() {
        return ircParsing.splitLastParameter(" ")
                         .map(Capability::find)
                         .flatMap(Optional::stream)
                         .collect(Collectors.toSet());
    }

    @NonNull
    public String lastParameter() {
        return ircParsing.lastParameter();
    }


    @NonNull
    public <T> T mapParameter(int parameterIndex, @NonNull Function<? super String, ? extends T> mapper) {
        return mapper.apply(ircParsing.getParams().parameterAt(parameterIndex));
    }

    @NonNull
    public TwitchChannel channelFromParameterAt(int parameterIndex) {
        return mapParameter(parameterIndex, TwitchChannel::create);
    }

    @NonNull
    public <T> T tagValue(@NonNull TagKey key, @NonNull Function1<? super String, ? extends T> mapper) {
        return mapper.apply(tagValue(key));
    }

    @NonNull
    public String tagValue(@NonNull TagKey key) {
        return optionalTagValue(key)
                .orElseThrow(() -> buildException("Could not find tag with name '"+key.name()+"'"));
    }

    @NonNull
    public Optional<String> optionalTagValue(@NonNull TagKey key) {
        return ircParsing.tagValue(key.getKeyName());
    }

    @NonNull
    public String tagValue(@NonNull TagKey key, @NonNull String defaultValue) {
        return optionalTagValue(key)
                .orElse(defaultValue);
    }

    @NonNull
    public <T> Optional<T> optionalTagValue(@NonNull TagKey key, @NonNull Function<? super String, ? extends T> mapper) {
        return optionalTagValue(key)
                .map(mapper);
    }

    public boolean tagValueAsBoolean(@NonNull TagKey key, boolean defaultValue) {
        final String value = optionalTagValue(key).orElse(null);
        return value == null?defaultValue:value.equals("1");
    }

    public int tagValueAsInt(@NonNull TagKey key, int defaultValue) {
        return optionalTagValueAsInt(key).orElse(defaultValue);
    }

    @NonNull
    public Optional<Integer> optionalTagValueAsInt(@NonNull TagKey key) {
        return optionalTagValue(key, Integer::parseInt);
    }


    @NonNull
    public String userFromPrefix() {
        return prefix().getNickOrServerName();
    }

    @NonNull
    private Prefix prefix() {
        return ircParsing.getPrefix()
                         .orElseThrow(() -> buildException("No prefix defined"));
    }

    @NonNull
    private IllegalArgumentException buildException(@NonNull String message) {
        return new IllegalArgumentException(message+". Raw Message:'"+ircParsing.getRawMessage()+"'");
    }

    @NonNull
    public String parameterAt(int parameterIndex) {
        return ircParsing.getParams().parameterAt(parameterIndex);
    }
}
