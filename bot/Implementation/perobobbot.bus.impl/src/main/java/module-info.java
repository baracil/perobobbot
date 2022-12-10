module perobobbot.bus.impl {
    requires static lombok;

    requires com.google.common;

    requires java.desktop;
    requires jakarta.inject;

    requires io.micronaut.runtime;

    requires perobobbot.api;
    requires perobobbot.bus.api;

}