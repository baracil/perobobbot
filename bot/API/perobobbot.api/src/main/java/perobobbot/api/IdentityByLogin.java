package perobobbot.api;

import lombok.NonNull;
import perobobbot.api.data.Platform;

public record IdentityByLogin(@NonNull Platform platform, @NonNull String login) implements Id {

    @Override
    public <T> @NonNull T accept(@NonNull Visitor<T> visitor) {
        return visitor.visit(this);
    }

}
