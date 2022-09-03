module perobobbot.data.service.api {
    requires static lombok;
    requires java.desktop;

    requires com.google.common;
    requires transitive perobobbot.data.io;
    requires transitive fpc.tools.cipher;

    exports perobobbot.data.service.api;
}