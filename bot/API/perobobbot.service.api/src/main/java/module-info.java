module perobobbot.service.api {
    requires static lombok;
    requires java.desktop;

    requires com.google.common;
    requires transitive perobobbot.api;
    requires transitive fpc.tools.cipher;

    requires io.micronaut.serde.serde_api;
    requires io.micronaut.core;

    exports perobobbot.service.api;
}