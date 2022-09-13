module perobobbot.data.web.client {

    requires io.micronaut.inject;
    requires io.micronaut.core;
    requires jakarta.inject;
    requires io.micronaut.http_client_core;

    requires transitive perobobbot.server.web.api;
    requires transitive fpc.tools.jackson;
    requires com.fasterxml.jackson.databind;

    exports perobobbot.data.web.client;

}