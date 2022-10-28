module perobobbot.twitch.web.api {
    requires static lombok;
    requires java.desktop;

    requires com.google.common;

    requires io.micronaut.serde.serde_api;

    requires fpc.tools.lang;
    requires fpc.tools.jackson;

    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.datatype.guava;
    requires com.fasterxml.jackson.datatype.jsr310;
    requires com.fasterxml.jackson.datatype.jdk8;

    requires jakarta.inject;

    requires perobobbot.api;
    requires perobobbot.twitch.api;
    requires org.slf4j;

    exports perobobbot.twitch.web.api;
    exports perobobbot.twitch.web.api.eventsub;
}