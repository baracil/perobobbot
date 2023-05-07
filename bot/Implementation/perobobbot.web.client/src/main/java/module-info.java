module perobobbot.web.client {

    requires io.micronaut.inject;
    requires io.micronaut.core;
    requires io.micronaut.aop;
    requires jakarta.inject;
    requires io.micronaut.http_client_core;

    requires transitive perobobbot.web.api;

    requires transitive fpc.tools.jackson;
    requires com.fasterxml.jackson.databind;
    requires fpc.tools.annotation;

    exports perobobbot.web.client;

}