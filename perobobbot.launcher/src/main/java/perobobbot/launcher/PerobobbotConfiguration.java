package perobobbot.launcher;

import fpc.tools.lang.Secret;
import io.micronaut.context.annotation.ConfigurationProperties;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.nio.file.Path;

@ConfigurationProperties("perobobbot")
@Getter @Setter
public class PerobobbotConfiguration {

    private @NonNull Data data;
    private @NonNull Plugin plugin;

    @ConfigurationProperties("data")
    @Getter @Setter
    public static class Data {
        private @NonNull Secret dbPassPhrase;
    }

    @ConfigurationProperties("plugin")
    @Getter @Setter
    public static class Plugin {
        private @NonNull Path folder;
    }

}
