module perobobbot.twitch.service.api {
    requires static lombok;
    requires java.desktop;

    requires io.micronaut.inject;
    requires io.micronaut.core;
    requires io.micronaut.aop;
    requires io.micronaut.runtime;

    requires jakarta.inject;

    requires perobobbot.twitch.api;
    requires perobobbot.api;
    requires fpc.tools.annotation;

    exports perobobbot.twitch.service.api;
}