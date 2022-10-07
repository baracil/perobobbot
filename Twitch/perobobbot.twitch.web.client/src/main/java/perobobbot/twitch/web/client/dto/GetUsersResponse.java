package perobobbot.twitch.web.client.dto;

import io.micronaut.serde.annotation.Serdeable;
import lombok.Value;

@Serdeable
public class GetUsersResponse {
    UserDTO[] data;

    @Value
    @Serdeable
    public static class UserDTO {
        String broadcaster_type;
        String description;
        String display_name;
        String id;
        String login;
        String offline_image_url;
        String profile_image_url;
        String type;
        int view_count;
        String email;
        String created_at;
    }
}
