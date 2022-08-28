module perobobbot.server.web.api {
    requires static lombok;
    requires java.desktop;

    requires com.google.common;
    requires io.micronaut.http;
    requires perobobbot.data.io;

    exports perobobbot.data.web.api;
}