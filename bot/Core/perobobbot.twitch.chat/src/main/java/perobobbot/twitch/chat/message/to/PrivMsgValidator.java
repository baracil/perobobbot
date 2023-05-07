package perobobbot.twitch.chat.message.to;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class PrivMsgValidator {

    public static String validate(String message) {
        return new PrivMsgValidator(message.trim()).validate();
    }

    private final String message;

    private String validate() {
        checkIsNotBlank();
        checkItIsNotACommand();
        return message;
    }

    private void checkIsNotBlank() {
        if (message.isBlank()) {
            throw createIllegalArgumentException("Message is blank");
        }
    }

    private void checkItIsNotACommand() {
        if (message.startsWith("/")) {
            throw createIllegalArgumentException("Use the command function to send a /command");
        }
    }

    private IllegalArgumentException createIllegalArgumentException(String errorMessage) {
        return new IllegalArgumentException("Invalid message : '" + message + "' : " + errorMessage);
    }
}
