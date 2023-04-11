module perobobbot.bus.impl {
    requires static lombok;


    requires java.desktop;
    requires jakarta.inject;

    requires io.micronaut.runtime;

    requires perobobbot.api;
    requires perobobbot.bus.api;

}