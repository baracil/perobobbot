package perobobbot.api.data;

import perobobbot.api.UserInfo;

public interface PlatformUserInfoProvider {

    Platform getPlatform();

    UserInfo getUserInfo(String userId);
}
