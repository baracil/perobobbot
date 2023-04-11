module perobobbot.bridge {
    requires static lombok;
    requires java.desktop;

    requires jakarta.inject;
    requires jakarta.annotation;
    requires io.micronaut.runtime;

    requires perobobbot.api;
    requires perobobbot.bus.api;
    requires perobobbot.service.api;
    requires perobobbot.chat.api;
    requires perobobbot.command.api;

    exports perobobbot.bridge;
}