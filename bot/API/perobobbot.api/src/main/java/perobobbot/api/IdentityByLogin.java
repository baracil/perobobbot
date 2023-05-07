package perobobbot.api;

import perobobbot.api.data.Platform;

public record IdentityByLogin(Platform platform, String login) implements Id {

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }

}
