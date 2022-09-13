module perobobbot.server.web.api {
    requires static lombok;
    requires java.desktop;

    requires com.google.common;
    requires jakarta.inject;

    requires io.micronaut.http;
    requires io.micronaut.core;
    requires io.micronaut.inject;

    requires transitive fpc.tools.cipher;
    requires transitive perobobbot.data.io;

    exports perobobbot.data.web.api;
}