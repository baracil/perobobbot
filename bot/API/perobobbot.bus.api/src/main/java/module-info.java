module perobobbot.bus.api {
    requires static lombok;
    requires java.desktop;

    requires perobobbot.api;
    requires com.google.common;

    exports perobobbot.bus.api;
}