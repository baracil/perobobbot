module perobobbot.twitch.oauth {
    requires static lombok;
    requires java.desktop;

    requires io.micronaut.core;
    requires io.micronaut.http;
    requires io.micronaut.aop;
    requires io.micronaut.http_client_core;


    requires java.sql;
    requires jakarta.inject;
    requires jakarta.annotation;

    requires io.micronaut.serde.serde_api;
    requires com.fasterxml.jackson.annotation;
    requires com.google.common;

    requires transitive perobobbot.api;
    requires transitive perobobbot.twitch.api;
    requires transitive perobobbot.oauth.api;


    exports perobobbot.twitch.oauth;
    exports perobobbot.twitch.oauth.dto;
}