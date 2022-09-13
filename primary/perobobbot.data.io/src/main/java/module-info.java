module perobobbot.data.io {
    requires static lombok;
    requires java.desktop;

    requires jakarta.inject;

    requires io.micronaut.serde.serde_api;

    requires io.micronaut.context;
    requires static io.micronaut.core;
    requires static io.micronaut.inject;

    requires fpc.tools.lang;
    requires fpc.tools.cipher;

    exports perobobbot.data.io;
    exports perobobbot.data.io.view;
}