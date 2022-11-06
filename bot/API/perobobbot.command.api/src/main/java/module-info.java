module perobobbot.command.api {
    requires static lombok;
    requires java.desktop;
    requires com.google.common;

    requires transitive perobobbot.api;

    exports perobobbot.command.api;
}