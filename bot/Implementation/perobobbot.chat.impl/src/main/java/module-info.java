module perobobbot.chat.impl {
    requires static lombok;
    requires java.desktop;

    requires com.google.common;

    requires perobobbot.chat.api;
    requires perobobbot.service.api;

    requires io.micronaut.core;
    requires io.micronaut.runtime;
    requires io.micronaut.context;
    requires jakarta.inject;
    requires jakarta.annotation;

    exports perobobbot.chat.impl;
}