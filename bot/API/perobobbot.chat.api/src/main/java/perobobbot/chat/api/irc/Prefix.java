package perobobbot.chat.api.irc;

import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.Optional;

@Value
@Builder
@RequiredArgsConstructor
public class Prefix {

    @NonNull String nickOrServerName;
    String user;
    String host;

    @NonNull
    public Optional<String> getUser() {
        return Optional.ofNullable(user);
    }

    @NonNull
    public Optional<String> getHost() {
        return Optional.ofNullable(host);
    }

    public static @NonNull Prefix fromFpc(@NonNull fpc.tools.irc.Prefix prefix) {
        return Prefix.builder()
                .nickOrServerName(prefix.getNickOrServerName())
                .user(prefix.user().orElse(null))
                .host(prefix.host().orElse(null))
                .build();
    }
}