package perobobbot.chat.api.irc;

import jakarta.annotation.Nullable;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.Optional;

@Value
@Builder
@RequiredArgsConstructor
public class Prefix {

    String nickOrServerName;
    @Nullable String user;
    @Nullable String host;

    public Optional<String> getUser() {
        return Optional.ofNullable(user);
    }

    public Optional<String> getHost() {
        return Optional.ofNullable(host);
    }

    public static Prefix fromFpc(fpc.tools.irc.Prefix prefix) {
        return Prefix.builder()
                .nickOrServerName(prefix.getNickOrServerName())
                .user(prefix.user().orElse(null))
                .host(prefix.host().orElse(null))
                .build();
    }
}