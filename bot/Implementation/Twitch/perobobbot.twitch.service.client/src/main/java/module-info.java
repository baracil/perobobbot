module perobobbot.twitch.service.client {
    requires static lombok;
    requires java.desktop;

    requires com.google.common;
    requires jakarta.inject;

    requires io.micronaut.inject;
    requires io.micronaut.core;
    requires io.micronaut.aop;
    requires io.micronaut.serde.serde_api;

    requires perobobbot.twitch.web.client;
    requires transitive perobobbot.twitch.api;
    requires transitive perobobbot.twitch.service.api;
    requires perobobbot.api;
    requires perobobbot.oauth.api;
    requires perobobbot.service.api;

    exports perobobbot.twitch.service.client;
}