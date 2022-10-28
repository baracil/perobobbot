open module perobobbot.launcher {
    uses perobobbot.oauth.api.OAuthManagerFactory;
    requires static lombok;
    requires java.desktop;

    requires io.micronaut.context;
    requires io.micronaut.core;
    requires io.micronaut.inject;
    requires io.micronaut.serde.serde_api;

    requires org.slf4j;
    requires jakarta.inject;
    requires java.annotation;
    requires java.sql;
    requires org.bouncycastle.provider;
    requires com.google.common;

    requires com.fasterxml.jackson.databind;

    requires perobobbot.api;
    requires perobobbot.oauth;
    requires perobobbot.service.api;
    requires perobobbot.twitch.chat;
    requires perobobbot.twitch.chat.impl;

    requires jplugman.base;

    requires fpc.tools.lang;
    requires fpc.tools.state;
    requires fpc.tools.serde;
    requires fpc.tools.cipher;
    requires fpc.tools.micronaut;
    requires io.micronaut.runtime;
    requires perobobbot.twitch.api;

    requires fpc.tools.advanced.chat;


    exports perobobbot.launcher;
}