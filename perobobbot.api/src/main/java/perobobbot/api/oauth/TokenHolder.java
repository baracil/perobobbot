package perobobbot.api.oauth;

import lombok.NonNull;

import java.util.Optional;

public interface TokenHolder extends DataHolder<TokenData> {


    @NonNull Optional<TokenData> get();

    /**
     * refresh the current token
     */
    void refreshUserToken();

}
