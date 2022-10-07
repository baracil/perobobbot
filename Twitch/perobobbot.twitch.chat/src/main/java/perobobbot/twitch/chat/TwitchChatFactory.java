package perobobbot.twitch.chat;

import lombok.NonNull;
import perobobbot.api.Identification;

public interface TwitchChatFactory {

    @NonNull TwitchChat create(@NonNull Identification identification, @NonNull String login);
}
