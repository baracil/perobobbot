module perobobbot.twitch.oauth {
    requires static lombok;
    requires java.desktop;

    requires io.micronaut.core;
    requires io.micronaut.http;
    requires io.micronaut.aop;
    requires io.micronaut.http_client_core;


    requires java.sql;
    requires jakarta.inject;

    requires perobobbot.api;
    requires perobobbot.twitch.api;
    requires io.micronaut.serde.serde_api;
    requires com.fasterxml.jackson.annotation;
    requires com.google.common;


    exports perobobbot.twitch.oauth;
    exports perobobbot.twitch.oauth.dto;
}