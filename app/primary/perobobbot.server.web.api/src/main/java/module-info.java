module perobobbot.server.web.api {
    exports perobobbot.server.web.api;

    requires static lombok;
    requires java.desktop;



    requires io.micronaut.http;
    requires com.google.common;

    requires perobobbot.server.io;

}