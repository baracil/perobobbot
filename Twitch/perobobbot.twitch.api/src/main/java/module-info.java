module perobobbot.twitch.api {
    requires static lombok;
    requires java.desktop;

    requires com.google.common;

    requires org.slf4j;

    requires perobobbot.api;
    requires com.fasterxml.jackson.annotation;
    requires io.micronaut.serde.serde_api;

    exports perobobbot.twitch.api;
}