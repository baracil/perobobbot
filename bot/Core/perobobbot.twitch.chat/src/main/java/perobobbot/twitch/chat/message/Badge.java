package perobobbot.twitch.chat.message;

import lombok.NonNull;
import lombok.Value;

@Value
public class Badge {

    @NonNull
    public static Badge with(@NonNull String name, @NonNull String version) {
        return new Badge(name, version);
    }

    @NonNull String name;

    @NonNull String version;
}
