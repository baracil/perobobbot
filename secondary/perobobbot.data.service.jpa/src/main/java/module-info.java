module perobobbot.data.service.jpa {
    requires static lombok;
    requires java.desktop;

    requires perobobbot.data.io;
    requires java.persistence;
    requires io.micronaut.data.data_model;
    requires io.micronaut.data.data_hibernate_jpa;
    requires jakarta.inject;
    requires java.transaction;
    requires com.google.common;
    requires perobobbot.data.service.api;


}