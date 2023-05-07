module perobobbot.service.jpa {
    requires static lombok;
    requires java.desktop;

    requires java.persistence;
    requires jakarta.inject;
    requires java.transaction;

    requires jplugman.annotation;

    requires org.hibernate.orm.core;

    requires io.micronaut.serde.serde_api;
    requires io.micronaut.serde.serde_jackson;
    requires io.micronaut.data.data_model;
    requires io.micronaut.data.data_hibernate_jpa;


    requires fpc.tools.lang;
    requires fpc.tools.hibernate;
    requires fpc.tools.persistence;
    requires fpc.tools.cipher;

    requires perobobbot.api;
    requires perobobbot.service.api;
    requires perobobbot.domain.jpa;
    requires io.micronaut.core;
    requires fpc.tools.annotation;


}