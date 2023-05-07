package perobobbot.twitch.chat.message;

import lombok.Value;

@Value
public class Badge {

    public static Badge with(String name, String version) {
        return new Badge(name, version);
    }

    String name;

    String version;
}
