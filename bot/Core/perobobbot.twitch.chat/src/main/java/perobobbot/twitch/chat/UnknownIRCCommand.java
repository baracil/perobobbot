package perobobbot.twitch.chat;

import lombok.Getter;

public class UnknownIRCCommand extends TwitchChatException {

    @Getter
    private final String commandInPayload;

    public UnknownIRCCommand(String commandInPayload) {
        this.commandInPayload = commandInPayload;
    }
}
