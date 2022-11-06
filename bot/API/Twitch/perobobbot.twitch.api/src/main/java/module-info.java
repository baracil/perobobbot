open module perobobbot.twitch.api {
    requires static lombok;
    requires java.desktop;

    requires com.google.common;

    requires org.slf4j;

    requires jakarta.inject;
    requires jakarta.annotation;

    requires io.micronaut.inject;
    requires io.micronaut.core;
    requires io.micronaut.serde.serde_api;

    requires com.fasterxml.jackson.annotation;

    requires fpc.tools.serde;

    requires perobobbot.api;
    requires io.micronaut.json_core;
    requires org.jetbrains.annotations;

    exports perobobbot.twitch.api;
    exports perobobbot.twitch.api.serde;
    exports perobobbot.twitch.api.eventsub;
    exports perobobbot.twitch.api.eventsub.event;
    exports perobobbot.twitch.api.eventsub.subscription;
}