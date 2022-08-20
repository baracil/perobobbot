module perobobbot.server.io {
    requires static lombok;
    requires java.desktop;

    requires io.micronaut.core;
    requires io.micronaut.inject;
    requires io.micronaut.serde.serde_api;

    exports perobobbot.server.io;
    exports perobobbot.server.io.view;

}