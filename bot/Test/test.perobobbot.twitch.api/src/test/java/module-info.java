open module test.perobobbot.twitch.api {
    requires static lombok;
    requires java.desktop;


    requires io.micronaut.core;
    requires io.micronaut.serde.serde_api;
    requires io.micronaut.test.test_core;
    requires io.micronaut.test.test_junit5;


    requires perobobbot.tools;
    requires perobobbot.twitch.oauth;
    requires perobobbot.twitch.api;


    requires jakarta.inject;
    requires org.junit.jupiter.api;
    requires org.junit.jupiter.params;
}