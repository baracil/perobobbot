open module perobobbot.api {
    requires static lombok;
    requires java.desktop;

    requires jakarta.inject;

    requires com.fasterxml.jackson.databind;

    requires io.micronaut.context;
    requires io.micronaut.runtime;
    requires io.micronaut.core;
    requires io.micronaut.inject;

    requires transitive fpc.tools.lang;
    requires transitive fpc.tools.cipher;
    requires transitive fpc.tools.micronaut;
    requires com.google.common;

    requires jplugman.annotation;
    requires io.micronaut.serde.serde_api;

    exports perobobbot.api.data.serde;
    exports perobobbot.api.data;

    exports perobobbot.api.plugin;
    exports perobobbot.api;
}