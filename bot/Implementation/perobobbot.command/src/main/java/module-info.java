module perobobbot.command {
    requires static lombok;
    requires java.desktop;
    requires fpc.tools.lang;
    requires com.google.common;

    requires org.slf4j;

    requires jakarta.inject;
    requires jakarta.annotation;

    requires transitive perobobbot.api;
    requires transitive perobobbot.chat.api;
    requires transitive perobobbot.twitch.chat;
    requires transitive perobobbot.command.api;


    requires io.micronaut.core;

    exports perobobbot.command.impl;
}