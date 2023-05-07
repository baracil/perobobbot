package perobobbot.twitch.oauth;

import fpc.tools.lang.Secret;
import io.micronaut.core.annotation.Introspected;
import lombok.Value;

import java.util.List;

@Introspected
@Value
public class UserToken {
    Secret access_token;
    int expires_in;
    Secret refresh_token;
    List<String> scope;
    String token_type;
}
