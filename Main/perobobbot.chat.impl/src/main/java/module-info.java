module perobobbot.chat.impl {
    requires static lombok;
    requires java.desktop;

    requires com.google.common;

    requires perobobbot.chat.api;
    requires perobobbot.service.api;

    requires jakarta.annotation;
    requires io.micronaut.core;
    requires jakarta.inject;

    exports perobobbot.chat.impl;
}