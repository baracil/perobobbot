module perobobbot.domain.jpa {
    requires static lombok;
    requires java.desktop;

    requires io.micronaut.data.data_model;
    requires io.micronaut.data.data_hibernate_jpa;
    requires java.persistence;

    requires fpc.tools.persistence;
    requires fpc.tools.hibernate;

    requires org.hibernate.orm.core;
    requires com.google.common;

    requires perobobbot.api;

    exports perobobbot.domain.jpa.entity to perobobbot.service.jpa;
    exports perobobbot.domain.jpa.repository to perobobbot.service.jpa;
}