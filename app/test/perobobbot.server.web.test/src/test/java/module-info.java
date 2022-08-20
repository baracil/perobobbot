module perobobbot.server.web.test {
    requires static lombok;
    requires java.desktop;

    requires com.google.common;
    requires io.micronaut.http_client_core;
    requires io.micronaut.inject;
    requires io.micronaut.context;
    requires io.micronaut.aop;
    requires io.micronaut.core;
    requires io.micronaut.test.test_core;
    requires io.micronaut.test.test_junit5;
    requires jakarta.inject;
    requires org.junit.jupiter.api;
    requires org.mockito;

    requires perobobbot.server.io;
    requires perobobbot.server.web.api;
    requires perobobbot.server.service.api;
    requires io.micronaut.http;
    requires org.reactivestreams;

    exports perobobbot.server.web.test to
            io.micronaut.core;
    opens perobobbot.server.web.test to
            org.junit.platform.commons,
            io.micronaut.test.test_junit5;

}