package perobobbot.launcher;

import fpc.tools.lang.Secret;
import io.micronaut.context.annotation.ConfigurationProperties;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@ConfigurationProperties("perobobbot.data")
@Getter @Setter
public class DataConfiguration {

    private @NonNull Secret dbPassPhrase;
}
