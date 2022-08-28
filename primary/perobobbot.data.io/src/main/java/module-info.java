module perobobbot.data.io {
    requires static lombok;
    requires java.desktop;

    requires io.micronaut.serde.serde_api;

    requires io.micronaut.context;
    requires static io.micronaut.core;
    requires static io.micronaut.inject;

    exports perobobbot.data.io;
    exports perobobbot.data.io.view;
}