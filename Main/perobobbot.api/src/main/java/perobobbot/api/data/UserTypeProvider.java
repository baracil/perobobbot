package perobobbot.api.data;


import lombok.NonNull;
import perobobbot.api.UserInfo;

public interface UserTypeProvider {

    @NonNull UserType getUserType(@NonNull UserInfo userInfo);

}
