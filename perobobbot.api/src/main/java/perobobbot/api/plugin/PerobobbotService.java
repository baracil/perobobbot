package perobobbot.api.plugin;

import io.micronaut.core.annotation.Introspected;

import java.lang.annotation.*;

@Introspected
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PerobobbotService {

    int apiVersion();

    Class<?> serviceType();

    String API_VERSION = "apiVersion";
    String SERVICE_TYPE = "serviceType";
}
