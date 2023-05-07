package perobobbot.twitch.chat;

import perobobbot.api.data.UserIdentity;

public interface TwitchChatFactory {

    TwitchChat create(UserIdentity userIdentity);
}
