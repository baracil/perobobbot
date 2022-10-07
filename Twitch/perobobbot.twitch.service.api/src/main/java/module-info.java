module perobobbot.twitch.service.api {
    requires static lombok;
    requires java.desktop;

    requires com.google.common;

    requires io.micronaut.inject;
    requires io.micronaut.core;
    requires io.micronaut.aop;

    requires perobobbot.twitch.api;

    exports perobobbot.twitch.service.api;
}