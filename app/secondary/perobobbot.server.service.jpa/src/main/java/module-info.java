module perobobbot.server.service.jpa {
    requires static lombok;
    requires java.desktop;


    requires java.persistence;

    requires java.transaction;
    requires jakarta.inject;
    requires com.google.common;

    requires io.micronaut.data.data_model;
    requires io.micronaut.data.data_hibernate_jpa;
    requires io.micronaut.inject;
    requires io.micronaut.core;


    requires perobobbot.server.io;
    requires perobobbot.server.service.api;
}