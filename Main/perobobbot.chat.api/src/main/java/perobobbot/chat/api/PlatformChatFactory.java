package perobobbot.chat.api;

import lombok.NonNull;
import perobobbot.api.data.Platform;
import perobobbot.api.data.view.UserIdentity;

public interface PlatformChatFactory {

    @NonNull Platform getPlatform();

    @NonNull Chat create(@NonNull UserIdentity userIdentity);
}
