package perobobbot.twitch.api.eventsub;

import fpc.tools.lang.Secret;
import io.micronaut.context.annotation.ConfigurationProperties;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@ConfigurationProperties("perobobbot.eventsub")
public class TwitchEventSubConfiguration {

    @Getter @Setter
    private Secret secret;

    @Getter @Setter
    private String callbackUrl;

    @Getter @Setter
    private boolean backupMessages;

}
