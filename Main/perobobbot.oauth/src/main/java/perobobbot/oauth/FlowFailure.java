package perobobbot.oauth;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FlowFailure extends RuntimeException {

    @Getter
    private final @NonNull Failure failure;
}
