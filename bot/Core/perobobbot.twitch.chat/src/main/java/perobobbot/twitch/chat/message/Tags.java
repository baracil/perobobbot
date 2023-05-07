package perobobbot.twitch.chat.message;

import fpc.tools.fp.Function1;
import fpc.tools.irc.Tag;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

/**
 * @author Bastien Aracil
 **/
public interface Tags {

    Optional<String> findTag(String tagName);

    default <T> Optional<T> flatFindTag(String tagName, Function1<? super String, ? extends Optional<? extends T>> caster) {
        return findTag(tagName).flatMap(caster);
    }

    default Optional<String> findTag(TagKey tagKey) {
        return findTag(tagKey.getKeyName());
    }

    default String getTag(TagKey tagKey) {
        return findTag(tagKey).orElseThrow(() -> new IllegalArgumentException("Could not find tag " + tagKey));
    }

    default String getTag(TagKey tagKey, String defaultValue) {
        return findTag(tagKey).orElse(defaultValue);
    }

    default int getIntTag(TagKey tagKey) {
        return Integer.parseInt(getTag(tagKey));
    }

    default Optional<Integer> findIntTag(TagKey tagKey) {
        return findTag(tagKey, Integer::parseInt);
    }

    default <T> Optional<T> findTag(TagKey tagKey, Function<? super String, ? extends T> mapper) {
        return findTag(tagKey).map(mapper);
    }

    default <T> Optional<T> flatFindTag(TagKey tagKey, Function<? super String, ? extends Optional<? extends T>> mapper) {
        return findTag(tagKey).flatMap(mapper);
    }


    static Tags mapBased(Map<String, Tag> tags) {
        return tagName -> {
            final Tag tag = tags.get(tagName);
            return tag == null ? Optional.empty() : Optional.of(tag.getValue());
        };
    }

}
