package perobobbot.api;

import perobobbot.api.data.Platform;

public record BotId(Platform platform) implements Id {

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }
}
