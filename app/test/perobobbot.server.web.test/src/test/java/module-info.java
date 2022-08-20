open module perobobbot.server.web.test {
    requires static lombok;
    requires java.desktop;

    requires com.fasterxml.jackson.core;
    requires com.google.common;
    requires io.micronaut.aop;
    requires io.micronaut.context;
    requires io.micronaut.core;
    requires io.micronaut.http;
    requires io.micronaut.http_client_core;
    requires io.micronaut.http_server_netty;
    requires io.micronaut.inject;
    requires io.micronaut.test.test_core;
    requires io.micronaut.test.test_junit5;
    requires jakarta.annotation;
    requires jakarta.inject;
    requires net.bytebuddy;
    requires org.apache.logging.log4j;
    requires org.junit.jupiter.api;
    requires org.junit.jupiter.engine;
    requires org.junit.jupiter.params;
    requires org.mockito;
    requires org.reactivestreams;

    requires perobobbot.server.io;
    requires perobobbot.server.web.api;
    requires perobobbot.server.service.api;
    requires perobobbot.server.web.controller;

    exports perobobbot.server.web.test;
//    opens perobobbot.server.web.test to
//            org.junit.platform.commons,
//            io.micronaut.test.test_junit5;

}