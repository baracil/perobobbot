module perobobbot.web.controller {
    requires static lombok;
    requires java.desktop;

    requires com.google.common;
    requires io.micronaut.http;
    requires io.swagger.v3.oas.annotations;
    requires jakarta.inject;
    requires reactor.core;
    requires io.micronaut.core;
    requires io.micronaut.serde.serde_api;
    requires io.micronaut.inject;
    requires io.micronaut.views.views_velocity;
    requires io.micronaut.views.views_core;


    requires perobobbot.web.api;
    requires perobobbot.service.api;
    requires perobobbot.twitch.api;
    requires perobobbot.twitch.service.api;
    requires io.micronaut.http_server;
    requires perobobbot.api;
    requires perobobbot.oauth;
    requires perobobbot.twitch.chat;

}