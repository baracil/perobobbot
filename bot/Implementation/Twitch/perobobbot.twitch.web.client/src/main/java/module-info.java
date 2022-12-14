module perobobbot.twitch.web.client {
    requires static lombok;
    requires java.desktop;

    requires io.micronaut.core;
    requires io.micronaut.aop;
    requires io.micronaut.http;
    requires io.micronaut.http_client_core;

    requires org.reactivestreams;

    requires perobobbot.twitch.api;
    requires perobobbot.api;

    requires com.google.common;

    requires jakarta.inject;
    requires perobobbot.oauth.api;


    exports perobobbot.twitch.web.client to perobobbot.twitch.service.client;
}