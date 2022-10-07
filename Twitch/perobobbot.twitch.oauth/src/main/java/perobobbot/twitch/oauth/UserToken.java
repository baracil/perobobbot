package perobobbot.twitch.oauth;

import fpc.tools.lang.Secret;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import lombok.NonNull;
import lombok.Value;

import java.util.List;

@Introspected
@Value
@Serdeable
public class UserToken {
    @NonNull Secret access_token;
    int expires_in;
    @NonNull Secret refresh_token;
    @NonNull List<String> scope;
    @NonNull String token_type;
}
