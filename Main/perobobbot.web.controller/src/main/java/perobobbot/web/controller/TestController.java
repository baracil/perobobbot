package perobobbot.web.controller;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.twitch.chat.TwitchChat;
import perobobbot.twitch.chat.message.to.MessageToTwitch;

@Controller("/api/v1/test")
@RequiredArgsConstructor
public class TestController {

    private final @NonNull TwitchChat twitchChat;

    @Get("/chat/{msg}")
    public void sendMessage(@NonNull @PathVariable String msg) {
        twitchChat.sendRequest(MessageToTwitch.join("perococco"))
                  .thenCompose(c -> twitchChat.sendCommand(MessageToTwitch.privateMsg("perococco", msg)));
    }
}
