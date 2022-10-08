package perobobbot.twitch.chat.impl._private.wrapping;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.chat.api.DispatchSlip;

import java.time.Instant;

@RequiredArgsConstructor
public class DispatchSlipWrapper implements DispatchSlip {

    private final @NonNull fpc.tools.advanced.chat.DispatchSlip twitchDispatchSlip;

    @Override
    public @NonNull Instant getDispatchingTime() {
        return twitchDispatchSlip.getDispatchingTime();
    }


}
