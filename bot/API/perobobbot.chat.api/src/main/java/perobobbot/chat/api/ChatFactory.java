package perobobbot.chat.api;

import lombok.NonNull;
import perobobbot.api.data.UserIdentity;

public interface ChatFactory {

    /**
     * Return the chat for the provided userIdentity. If the chat does not exist
     * it will be created and the connection will be made with the credential
     * associated with the provided user.</p>
     *
     * If no credential can be found, this method will fail
     *
     * @param userIdentity the user identity to use to connect to the chat
     * @return the chat for the provided userIdentity
     */
    @NonNull Chat create(@NonNull UserIdentity userIdentity) throws InterruptedException;

}
