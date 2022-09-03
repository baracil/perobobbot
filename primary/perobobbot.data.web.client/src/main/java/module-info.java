module perobobbot.data.web.client {

    requires io.micronaut.http_client_core;

    requires transitive perobobbot.server.web.api;

    exports perobobbot.data.web.client;

}