package perobobbot.oauth.api;

public sealed interface Failure {

    record Cancelled() implements Failure {}
    record TimedOut() implements Failure {}
    record Error(String message) implements Failure {}

}
