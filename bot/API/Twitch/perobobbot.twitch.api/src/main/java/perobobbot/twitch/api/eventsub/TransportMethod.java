package perobobbot.twitch.api.eventsub;


import fpc.tools.lang.IdentifiedEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum TransportMethod implements IdentifiedEnum {
    WEBHOOK("webhook"),
    ;

    @Getter
    private final String identification;

}
