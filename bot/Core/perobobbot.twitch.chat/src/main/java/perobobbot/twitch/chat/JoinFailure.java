package perobobbot.twitch.chat;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.twitch.chat.message.from.NoticeId;

@RequiredArgsConstructor
@Getter
public class JoinFailure extends TwitchChatException{

    @NonNull
    private final NoticeId msgId;

    @NonNull
    private final String message;


}
