module perobobbot.command {
    requires static lombok;
    requires java.desktop;
    requires fpc.tools.lang;
    requires com.google.common;

    requires org.slf4j;

    requires io.micronaut.serde.serde_api;

    requires perobobbot.api;

    exports perobobbot.command;
    exports perobobbot.command._private;
    exports perobobbot.command._private.parser;
}