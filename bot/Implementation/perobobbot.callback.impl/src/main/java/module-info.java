module perobobbot.callback.impl {
    requires static lombok;
    requires java.desktop;

    requires io.micronaut.http;
    requires jakarta.inject;

    requires io.micronaut.core;
    requires transitive perobobbot.callback.api;
    requires perobobbot.web.api;
    requires fpc.tools.annotation;

    exports perobobbot.callback.impl;
}