module perobobbot.chat.api {
    requires static lombok;
    requires java.desktop;

    requires fpc.tools.irc;

    requires io.micronaut.serde.serde_api;
    requires io.micronaut.serde.serde_jackson;
    requires com.fasterxml.jackson.annotation;

    requires transitive perobobbot.api;
    requires fpc.tools.annotation;


    exports perobobbot.chat.api;
    exports perobobbot.chat.api.irc;
}