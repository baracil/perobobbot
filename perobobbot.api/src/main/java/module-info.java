open module perobobbot.api {
    requires static lombok;
    requires java.desktop;

    requires jakarta.inject;

    requires io.micronaut.serde.serde_api;

    requires io.micronaut.context;
    requires io.micronaut.core;
    requires io.micronaut.inject;

    requires transitive fpc.tools.lang;
    requires transitive fpc.tools.cipher;
    requires transitive fpc.tools.micronaut;
    requires com.google.common;

    requires jplugman.annotation;

    exports perobobbot.api.data.view;
    exports perobobbot.api.data.serde;
    exports perobobbot.api.data;

    exports perobobbot.api.oauth;
    exports perobobbot.api.plugin;
    exports perobobbot.api;
}