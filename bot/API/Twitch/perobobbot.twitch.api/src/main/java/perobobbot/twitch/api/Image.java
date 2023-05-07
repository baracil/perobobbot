package perobobbot.twitch.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Value;

import java.net.URL;

@Value
@Serdeable
public class Image {
    @JsonProperty("url_1x")
    URL url1x;
    @JsonProperty("url_2x")
    URL url2x;
    @JsonProperty("url_4x")
    URL url4x;
}
