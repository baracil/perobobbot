package perobobbot.api;

import lombok.NonNull;
import perobobbot.api.data.Platform;

public record BotId(@NonNull Platform platform) implements Id {

    @Override
    public <T> @NonNull T accept(@NonNull Visitor<T> visitor) {
        return visitor.visit(this);
    }
}
