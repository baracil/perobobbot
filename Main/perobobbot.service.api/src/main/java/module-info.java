module perobobbot.service.api {
    requires static lombok;
    requires java.desktop;

    requires com.google.common;
    requires transitive perobobbot.api;
    requires transitive fpc.tools.cipher;

    exports perobobbot.service.api;
}