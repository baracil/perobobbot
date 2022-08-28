module perobobbot.server.web.api {
    requires static lombok;
    requires java.desktop;

    requires com.google.common;
    requires io.micronaut.http;
    requires perobobbot.server.io;

    exports perobobbot.data.web.api;
}