module perobobbot.callback.impl {
    requires static lombok;
    requires java.desktop;

    requires io.micronaut.http;
    requires perobobbot.web.api;
    requires jakarta.inject;
    requires com.google.common;

    requires transitive perobobbot.callback.api;

    exports perobobbot.callback.impl;
}