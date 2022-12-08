package perobobbot.discord.oauth;

import fpc.tools.lang.Secret;
import fpc.tools.lang.Todo;
import lombok.NonNull;
import perobobbot.api.data.Application;
import perobobbot.api.data.ApplicationToken;
import perobobbot.api.data.Platform;
import perobobbot.api.data.TokenWithIdentity;
import perobobbot.oauth.api.PlatformOAuth;

import java.net.URI;
import java.util.concurrent.CompletionStage;

public class DiscordOAuth implements PlatformOAuth {

    @Override
    public @NonNull Platform platform() {
        return Todo.TODO();
    }

    @Override
    public @NonNull URI getAuthorizationCodeGrantFlowURI(@NonNull String clientId, @NonNull String state, boolean forceVerify) {
        return Todo.TODO();
    }

    @Override
    public @NonNull CompletionStage<TokenWithIdentity> finalizeAuthorizationCodeGrantFlow(@NonNull Application<Secret> application, @NonNull String code) {
        return Todo.TODO();
    }

    @Override
    public @NonNull TokenWithIdentity refreshUserToken(@NonNull Application<Secret> application, @NonNull Secret refreshToken) {
        return Todo.TODO();
    }

    @Override
    public @NonNull ApplicationToken.Decrypted getAppToken(@NonNull Application<Secret> application) {
        return Todo.TODO();
    }

    @Override
    public void revoke(@NonNull String clientId, @NonNull Secret accessToken) {
        Todo.TODO();
    }

    @Override
    public void validate(@NonNull Secret accessToken) {
        Todo.TODO();
    }
}
