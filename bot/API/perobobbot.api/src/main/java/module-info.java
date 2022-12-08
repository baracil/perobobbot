open module perobobbot.api {
    requires static lombok;
    requires java.desktop;

    requires jakarta.inject;

    requires io.micronaut.context;
    requires io.micronaut.runtime;
    requires io.micronaut.core;
    requires io.micronaut.inject;
    requires io.micronaut.serde.serde_api;

    requires jakarta.annotation;

    requires com.fasterxml.jackson.annotation;

    requires transitive fpc.tools.lang;
    requires transitive fpc.tools.cipher;
    requires transitive fpc.tools.micronaut;
    requires com.google.common;

    requires jplugman.annotation;
    requires jplugman.api;

    exports perobobbot.api.serder;
    exports perobobbot.api.data;
    exports perobobbot.api.plugin;
    exports perobobbot.api;
    exports perobobbot.api.bus;
    exports perobobbot.api.bus.fallback;
}