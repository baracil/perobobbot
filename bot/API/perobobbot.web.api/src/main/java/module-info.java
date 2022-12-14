module perobobbot.web.api {
    requires static lombok;
    requires java.desktop;

    requires com.google.common;
    requires jakarta.inject;

    requires io.micronaut.http;
    requires io.micronaut.core;
    requires io.micronaut.inject;

    requires transitive perobobbot.api;
    requires transitive perobobbot.service.api;

    requires transitive fpc.tools.cipher;
    requires jakarta.annotation;

    exports perobobbot.web.api;
}