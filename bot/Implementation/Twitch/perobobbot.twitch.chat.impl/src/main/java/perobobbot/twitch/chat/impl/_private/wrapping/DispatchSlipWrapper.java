package perobobbot.twitch.chat.impl._private.wrapping;

import lombok.RequiredArgsConstructor;
import perobobbot.chat.api.DispatchSlip;

import java.time.Instant;

@RequiredArgsConstructor
public class DispatchSlipWrapper implements DispatchSlip {

    private final fpc.tools.advanced.chat.DispatchSlip twitchDispatchSlip;

    @Override
    public Instant getDispatchingTime() {
        return twitchDispatchSlip.getDispatchingTime();
    }


}
