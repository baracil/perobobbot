module perobobbot.service.api {
    requires static lombok;
    requires java.desktop;

    requires transitive perobobbot.api;
    requires transitive fpc.tools.cipher;

    requires com.fasterxml.jackson.annotation;

    requires io.micronaut.serde.serde_api;
    requires io.micronaut.core;
    requires fpc.tools.annotation;

    exports perobobbot.service.api;
}