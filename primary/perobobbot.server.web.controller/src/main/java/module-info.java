module perobobbot.server.web.controller {
    requires static lombok;
    requires java.desktop;


    requires io.micronaut.http;
    requires io.micronaut.inject;
    requires io.micronaut.core;
    requires io.swagger.v3.oas.annotations;
    requires jakarta.inject;
    requires reactor.core;
    requires org.reactivestreams;

    requires perobobbot.server.io;
    requires perobobbot.server.service.api;
    requires perobobbot.server.web.api;
    requires com.google.common;
}