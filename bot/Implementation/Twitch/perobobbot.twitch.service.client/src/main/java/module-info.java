module perobobbot.twitch.service.client {
    requires static lombok;
    requires java.desktop;

    requires com.google.common;
    requires jakarta.inject;

    requires io.micronaut.inject;
    requires io.micronaut.core;
    requires io.micronaut.aop;

    requires perobobbot.twitch.web.client;
    requires transitive perobobbot.twitch.api;
    requires transitive perobobbot.twitch.service.api;
    requires perobobbot.api;
    requires perobobbot.oauth.api;

    exports perobobbot.twitch.service.client;
}