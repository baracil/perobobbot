package perobobbot.discord.oauth;

import fpc.tools.lang.Secret;
import fpc.tools.lang.Todo;
import perobobbot.api.data.Application;
import perobobbot.api.data.ApplicationToken;
import perobobbot.api.data.Platform;
import perobobbot.api.data.TokenWithIdentity;
import perobobbot.oauth.api.PlatformOAuth;

import java.net.URI;
import java.util.concurrent.CompletionStage;

public class DiscordOAuth implements PlatformOAuth {

    @Override
    public Platform platform() {
        return Todo.TODO();
    }

    @Override
    public URI getAuthorizationCodeGrantFlowURI(String clientId, String state, boolean forceVerify) {
        return Todo.TODO();
    }

    @Override
    public CompletionStage<TokenWithIdentity> finalizeAuthorizationCodeGrantFlow(Application<Secret> application, String code) {
        return Todo.TODO();
    }

    @Override
    public TokenWithIdentity refreshUserToken(Application<Secret> application, Secret refreshToken) {
        return Todo.TODO();
    }

    @Override
    public ApplicationToken.Decrypted getAppToken(Application<Secret> application) {
        return Todo.TODO();
    }

    @Override
    public void revoke(String clientId, Secret accessToken) {
        Todo.TODO();
    }

    @Override
    public void validate(Secret accessToken) {
        Todo.TODO();
    }
}
