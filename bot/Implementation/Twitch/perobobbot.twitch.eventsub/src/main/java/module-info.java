module perobobbot.twitch.eventsub {
    requires static lombok;
    requires java.desktop;

    requires io.micronaut.http;
    requires io.micronaut.inject;
    requires io.micronaut.context;

    requires fpc.tools.lang;

    requires jakarta.inject;
    requires jakarta.annotation;
    requires org.slf4j;

    requires io.micronaut.serde.serde_api;

    requires perobobbot.tools;
    requires perobobbot.api;
    requires perobobbot.twitch.api;
    requires perobobbot.bus.api;
    requires perobobbot.callback.api;
    requires perobobbot.service.api;
    requires perobobbot.twitch.service.api;
    requires fpc.tools.annotation;


    exports perobobbot.twitch.eventsub;
    exports perobobbot.twitch.eventsub.callback;
    exports perobobbot.twitch.eventsub.sync;
}