module perobobbot.twitch.chat {
    requires static lombok;
    requires java.desktop;

    requires com.google.common;

    requires transitive fpc.tools.advanced.chat;
    requires fpc.tools.lang;
    requires fpc.tools.irc;

    requires transitive perobobbot.api;
    requires transitive perobobbot.twitch.api;
    requires io.github.bucket4j.core;

    exports perobobbot.twitch.chat;
    exports perobobbot.twitch.chat.message;
    exports perobobbot.twitch.chat.message.from;
    exports perobobbot.twitch.chat.message.to;
}