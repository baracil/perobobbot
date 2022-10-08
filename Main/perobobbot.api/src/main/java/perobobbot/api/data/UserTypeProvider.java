package perobobbot.api.data;


import lombok.NonNull;
import perobobbot.api.UserInfo;

public interface UserTypeProvider {

    @NonNull UserIdentityType getUserType(@NonNull UserInfo userInfo);

}
