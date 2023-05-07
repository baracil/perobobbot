package perobobbot.twitch.chat;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import perobobbot.twitch.chat.message.from.NoticeId;

@RequiredArgsConstructor
@Getter
public class JoinFailure extends TwitchChatException{

    private final NoticeId msgId;

    private final String message;


}
