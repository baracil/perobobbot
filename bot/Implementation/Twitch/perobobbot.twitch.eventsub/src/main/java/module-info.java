module perobobbot.twitch.eventsub {
    requires static lombok;
    requires java.desktop;

    requires io.micronaut.http;

    requires fpc.tools.lang;

    requires perobobbot.api;
    requires perobobbot.callback.api;

    exports perobobbot.twitch.eventsub;
}