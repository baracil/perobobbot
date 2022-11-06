package perobobbot.twitch.web.client;

import io.micronaut.core.annotation.Introspected;
import lombok.Value;

@Introspected
@Value
public class GetUsersResponse {
    UserDTO[] data;

    @Value
    @Introspected
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
