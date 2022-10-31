package perobobbot.launcher;

import fpc.tools.lang.Secret;
import io.micronaut.context.annotation.ConfigurationProperties;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import perobobbot.api.data.Platform;

import java.nio.file.Path;
import java.util.Map;

@ConfigurationProperties("perobobbot")
@Getter @Setter
public class PerobobbotConfiguration {

    private @NonNull Data data;
    private @NonNull Plugin plugin;
    private @NonNull OAuth oauth;

    @ConfigurationProperties("data")
    @Getter @Setter
    public static class Data {
        private @NonNull Secret dbPassPhrase;
    }

    @ConfigurationProperties("oauth")
    @Getter @Setter
    public static class OAuth {
        private @NonNull Map<Platform,String> defaultIds;
    }


    @ConfigurationProperties("plugin")
    @Getter @Setter
    public static class Plugin {
        private @NonNull Path folder;
    }

}
