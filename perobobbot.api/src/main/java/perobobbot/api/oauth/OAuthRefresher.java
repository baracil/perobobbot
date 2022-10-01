package perobobbot.api.oauth;

import lombok.NonNull;

public interface OAuthRefresher {

    @NonNull AuthData refresh(@NonNull AuthData authData, @NonNull OAuthAccessMode mode);

    @NonNull AuthData prepare(@NonNull AuthData authData, @NonNull OAuthAccessMode mode);
}
