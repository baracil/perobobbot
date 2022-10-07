package perobobbot.twitch.api;

import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

/**
 * @author perococco
 **/
public class TwitchMarkers {

    public static final Marker TWITCH = MarkerFactory.getMarker("TWITCH");
    public static final Marker TWITCH_CHAT = MarkerFactory.getMarker("TWITCH_CHAT");


    static {
        TWITCH_CHAT.add(TWITCH);
    }
}
