package perobobbot.api.plugin;

import io.micronaut.core.annotation.Introspected;

import java.lang.annotation.*;

@Introspected
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PerobobbotServices {

    PerobobbotService[] value();

}
