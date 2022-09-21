module perobobbot.service.jpa {
    requires static lombok;
    requires java.desktop;

    requires perobobbot.api;

    requires java.persistence;
    requires jakarta.inject;
    requires java.transaction;
    requires com.google.common;


    requires org.hibernate.orm.core;

    requires io.micronaut.data.data_model;
    requires io.micronaut.data.data_hibernate_jpa;

    requires fpc.tools.lang;
    requires fpc.tools.hibernate;
    requires fpc.tools.persistence;
    requires fpc.tools.cipher;

    requires perobobbot.service.api;


}