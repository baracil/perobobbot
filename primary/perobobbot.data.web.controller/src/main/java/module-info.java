module perobobbot.data.web.controller {
    requires static lombok;
    requires java.desktop;

    requires com.google.common;
    requires io.micronaut.http;
    requires io.swagger.v3.oas.annotations;
    requires jakarta.inject;
    requires reactor.core;
    requires io.micronaut.core;

    requires perobobbot.server.web.api;
    requires perobobbot.data.service.api;

}