module perobobbot.oauth {
    requires static lombok;
    requires java.desktop;
    requires com.google.common;
    requires perobobbot.api;
    requires perobobbot.service.api;
    requires org.slf4j;


    exports perobobbot.oauth;
}