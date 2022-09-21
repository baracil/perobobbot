module perobobbot.web.client {

    requires io.micronaut.inject;
    requires io.micronaut.core;
    requires jakarta.inject;
    requires io.micronaut.http_client_core;
    requires io.micronaut.aop;

    requires transitive perobobbot.web.api;

    requires transitive fpc.tools.jackson;
    requires com.fasterxml.jackson.databind;

    exports perobobbot.web.client;

}