import perobobbot.oauth.api.OAuthManagerFactory;
import perobobbot.oauth.impl.DefaultOAuthManagerFactory;

module perobobbot.oauth {
    requires static lombok;
    requires java.desktop;
    requires com.google.common;
    requires perobobbot.api;
    requires perobobbot.service.api;
    requires org.slf4j;

    requires transitive perobobbot.oauth.api;

    provides OAuthManagerFactory with DefaultOAuthManagerFactory;
}