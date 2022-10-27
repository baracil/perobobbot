module perobobbot.twitch.web.dto {
    requires static lombok;
    requires java.desktop;
    requires com.google.common;
    requires fpc.tools.lang;

    requires io.micronaut.serde.serde_api;
    requires perobobbot.twitch.api;
    requires perobobbot.api;

    exports perobobbot.twitch.web.dto;
}