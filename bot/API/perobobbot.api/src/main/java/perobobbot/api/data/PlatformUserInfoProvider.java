package perobobbot.api.data;

import lombok.NonNull;
import perobobbot.api.UserInfo;

public interface PlatformUserInfoProvider {

    @NonNull Platform getPlatform();

    @NonNull UserInfo getUserInfo(@NonNull String userId);
}
