package perobobbot.twitch.api.eventsub;

public record ObjectWithLogin<T>(String login, T value) {

    public static <T> ObjectWithLogin<T> create(String login, T value) {
        return new ObjectWithLogin<>(login,value);
    }

}
