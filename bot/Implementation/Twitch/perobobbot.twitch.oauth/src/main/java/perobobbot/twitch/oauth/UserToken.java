package perobobbot.twitch.oauth;

import fpc.tools.lang.Secret;
import io.micronaut.core.annotation.Introspected;
import lombok.NonNull;
import lombok.Value;

import java.util.List;

@Introspected
@Value
public class UserToken {
    @NonNull Secret access_token;
    int expires_in;
    @NonNull Secret refresh_token;
    @NonNull List<String> scope;
    @NonNull String token_type;
}
