open module perobobbot.launcher {
    requires static lombok;
    requires java.desktop;

    requires io.micronaut.context;
    requires io.micronaut.core;
    requires io.micronaut.inject;
    requires io.micronaut.serde.serde_api;

    requires org.slf4j;
    requires jakarta.inject;
    requires java.annotation;
    requires java.sql;
    requires org.bouncycastle.provider;
    requires com.google.common;

    requires discovery.zookeeper.api;
    requires discovery.zookeeper.curator;

    requires perobobbot.api;
    requires perobobbot.oauth;
    requires perobobbot.service.api;

    requires jplugman.base;

    requires fpc.tools.serde;
    requires fpc.tools.cipher;
    requires fpc.tools.micronaut;
    requires io.micronaut.runtime;


    exports perobobbot.launcher;
}