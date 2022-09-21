package perobobbot.oauth;

import lombok.NonNull;

public sealed interface Failure {

    record Cancelled() implements Failure {}
    record TimedOut() implements Failure {}
    record Error(@NonNull String message) implements Failure {}

}
