package perobobbot.launcher;

import fpc.tools.lang.Instants;
import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;

@Factory
public class InstantsFactory {

    @Bean
    public Instants instants() {
        return Instants.systemUTC();
    }
}
