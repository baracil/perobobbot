module perobobbot.twitch.web.client {
    requires static lombok;
    requires java.desktop;
    requires io.micronaut.aop;
    requires io.micronaut.http;
    requires io.micronaut.http_client_core;
    requires perobobbot.twitch.api;
    requires org.reactivestreams;
    requires perobobbot.api;
    requires com.google.common;
    requires jakarta.inject;
    requires io.micronaut.serde.serde_api;


    exports perobobbot.twitch.web.client to perobobbot.twitch.service.client;
    exports perobobbot.twitch.web.client.dto to perobobbot.twitch.service.client;
}