package perobobbot.api.oauth;

import fpc.tools.lang.ValueHolder;
import lombok.NonNull;

public interface AuthHolder extends ValueHolder<AuthData> {

    void refreshToken(@NonNull OAuthAccessMode mode);

    void prepareToken(@NonNull OAuthAccessMode oAuthAccessMode);
}
