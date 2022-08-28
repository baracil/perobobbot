module perobobbot.data.launcher {
    requires static lombok;
    requires java.desktop;

    requires io.micronaut.context;
    requires static io.micronaut.core;
    requires static io.micronaut.inject;

    requires org.slf4j;
    requires jakarta.inject;
    requires discovery.zookeeper.api;
    requires discovery.zookeeper.curator;
    requires java.annotation;
    requires java.sql;

    exports perobobbot.data.launcher;
}