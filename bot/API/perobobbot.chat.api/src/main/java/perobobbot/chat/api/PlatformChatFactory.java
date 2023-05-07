package perobobbot.chat.api;

import perobobbot.api.data.Platform;
import perobobbot.api.data.UserIdentity;

public interface PlatformChatFactory {

    Platform getPlatform();

    Chat create(UserIdentity userIdentity);
}
