package perobobbot.api;

import java.util.Map;

public interface SerDeHelper {

    String serializeMap(Map<String,String> map);
    Map<String,String> deserializeMap(String data);
}
