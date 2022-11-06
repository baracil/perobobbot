module perobobbot.command {
    requires static lombok;
    requires java.desktop;
    requires fpc.tools.lang;
    requires com.google.common;

    requires org.slf4j;

    requires jakarta.inject;

    requires transitive perobobbot.api;
    requires transitive perobobbot.command.api;

    exports perobobbot.command.impl;
}