package perobobbot.twitch.chat.message;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.Set;

/**
 * @author Bastien Aracil
 **/
@RequiredArgsConstructor
public enum Capability {
    COMMANDS("twitch.tv/commands"),
    TAGS("twitch.tv/tags"),
    MEMBERSHIP("twitch.tv/membership"),
    ;

    @NonNull
    @Getter
    private final String ircValue;

    public static Optional<Capability> find(@NonNull String ircValue) {
        for (Capability value : Holder.VALUES) {
            if (value.ircValue.equals(ircValue)) {
                return Optional.of(value);
            }
        }
        return Optional.empty();
    }

    @NonNull
    public static Set<Capability> AllCapabilities() {
        return Holder.VALUES;
    }

    private static class Holder {

        private static final Set<Capability> VALUES = Set.of(values());
    }
}
