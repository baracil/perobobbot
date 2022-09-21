open module perobobbot.launcher {
    requires static lombok;
    requires java.desktop;

    requires io.micronaut.context;
    requires io.micronaut.core;
    requires io.micronaut.inject;
    requires io.micronaut.serde.serde_api;

    requires org.slf4j;
    requires jakarta.inject;
    requires discovery.zookeeper.api;
    requires discovery.zookeeper.curator;
    requires java.annotation;
    requires java.sql;
    requires org.bouncycastle.provider;

    requires perobobbot.oauth;
    requires perobobbot.twitch.oauth;

    requires fpc.tools.serde;
    requires fpc.tools.cipher;
    requires fpc.tools.micronaut;
    requires perobobbot.service.api;
    requires com.google.common;

    exports perobobbot.launcher;
}