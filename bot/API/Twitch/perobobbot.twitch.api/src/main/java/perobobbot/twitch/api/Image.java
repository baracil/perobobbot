package perobobbot.twitch.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.serde.annotation.Serdeable;
import lombok.NonNull;
import lombok.Value;

import java.net.URL;

@Value
@Serdeable
public class Image {
    @JsonProperty("url_1x")
    @NonNull URL url1x;
    @JsonProperty("url_2x")
    @NonNull URL url2x;
    @JsonProperty("url_4x")
    @NonNull URL url4x;
}
