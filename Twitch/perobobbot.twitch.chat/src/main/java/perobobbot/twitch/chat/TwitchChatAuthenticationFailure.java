package perobobbot.twitch.chat;

import lombok.Getter;
import lombok.NonNull;

public class TwitchChatAuthenticationFailure extends TwitchChatException {

    @NonNull
    @Getter
    private final String noticeMessage;

    public TwitchChatAuthenticationFailure(@NonNull String noticeMessage) {
        super("Authentication failed : "+noticeMessage);
        this.noticeMessage = noticeMessage;
    }
}
