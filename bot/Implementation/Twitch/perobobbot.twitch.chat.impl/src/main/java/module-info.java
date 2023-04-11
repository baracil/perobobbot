module perobobbot.twitch.chat.impl {
    requires static lombok;
    requires java.desktop;

    requires jakarta.inject;

    requires fpc.tools.lang;
    requires fpc.tools.advanced.chat;
    requires fpc.tools.state.chat;
    requires fpc.tools.irc;

    requires perobobbot.api;
    requires perobobbot.chat.api;
    requires perobobbot.bus.api;

    requires perobobbot.twitch.oauth;
    requires perobobbot.twitch.api;
    requires perobobbot.twitch.chat;

    requires io.micronaut.inject;
    requires io.micronaut.core;
    requires io.micronaut.runtime;

    exports perobobbot.twitch.chat.impl;

}