package perobobbot.twitch.chat;

import lombok.Getter;

public class TwitchChatAuthenticationFailure extends TwitchChatException {

    @Getter
    private final String noticeMessage;

    public TwitchChatAuthenticationFailure(String noticeMessage) {
        super("Authentication failed : "+noticeMessage);
        this.noticeMessage = noticeMessage;
    }
}
