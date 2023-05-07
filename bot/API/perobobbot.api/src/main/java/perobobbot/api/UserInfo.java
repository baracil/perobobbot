package perobobbot.api;

import perobobbot.api.data.Platform;

public record UserInfo(
        Identity identity,
        String login,
        String name) implements Identity.Provider {

    public Platform platform() {
        return identity.platform();
    }

    public String userId() {
        return identity.userId();
    }
}
