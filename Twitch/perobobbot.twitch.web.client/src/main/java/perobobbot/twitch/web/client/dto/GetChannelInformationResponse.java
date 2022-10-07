package perobobbot.twitch.web.client.dto;

import com.google.common.collect.ImmutableList;
import io.micronaut.serde.annotation.Serdeable;
import lombok.NonNull;
import lombok.Value;
import perobobbot.api.Identification;
import perobobbot.api.UserInfo;
import perobobbot.twitch.api.Channel;
import perobobbot.twitch.api.Game;
import perobobbot.twitch.api.Twitch;

import java.util.Arrays;
import java.util.Optional;

@Value
@Serdeable
public class GetChannelInformationResponse {

    ChannelDTO[] data;

    public @NonNull ImmutableList<Channel> toChannels() {
        return Optional.ofNullable(data)
                       .stream()
                       .flatMap(Arrays::stream)
                       .map(ChannelDTO::toChannel)
                       .collect(ImmutableList.toImmutableList());
    }

    @Value
    @Serdeable
    public static class ChannelDTO {
        String broadcaster_id;
        String broadcaster_login;
        String broadcaster_name;
        String game_name;
        String game_id;
        String broadcaster_language;
        String title;
        int delay;

        public @NonNull Channel toChannel() {
            return new Channel(
                    new UserInfo(new Identification(Twitch.PLATFORM,broadcaster_id), broadcaster_login, broadcaster_name),
                    new Game(game_name, game_id),
                    broadcaster_language,
                    title,
                    delay);
        }
    }
}
