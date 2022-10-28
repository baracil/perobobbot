package perobobbot.twitch.chat;

import lombok.NonNull;
import perobobbot.api.data.UserIdentity;

public interface TwitchChatFactory {

    @NonNull TwitchChat create(@NonNull UserIdentity userIdentity);
}
