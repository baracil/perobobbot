package perobobbot.launcher;

import fpc.tools.lang.Secret;
import io.micronaut.context.annotation.ConfigurationProperties;
import lombok.Getter;
import lombok.Setter;
import perobobbot.api.data.Platform;

import java.nio.file.Path;
import java.util.Map;

@ConfigurationProperties("perobobbot")
@Getter @Setter
public class PerobobbotConfiguration {

    private Data data;
    private Plugin plugin;
    private OAuth oauth;

    @ConfigurationProperties("data")
    @Getter @Setter
    public static class Data {
        private Secret dbPassPhrase;
    }

    @ConfigurationProperties("oauth")
    @Getter @Setter
    public static class OAuth {
        private Map<Platform,String> defaultIds;
    }


    @ConfigurationProperties("plugin")
    @Getter @Setter
    public static class Plugin {
        private Path folder;
    }

}
