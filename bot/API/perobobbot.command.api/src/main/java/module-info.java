module perobobbot.command.api {
    requires static lombok;
    requires java.desktop;


    requires io.micronaut.serde.serde_api;
    requires com.fasterxml.jackson.annotation;
    requires transitive perobobbot.api;
    requires fpc.tools.annotation;

    exports perobobbot.command.api;
}