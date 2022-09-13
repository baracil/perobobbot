package perobobbot.data.web.api;

import fpc.tools.lang.Secret;
import io.micronaut.context.annotation.Factory;
import io.micronaut.core.convert.TypeConverter;
import jakarta.inject.Singleton;
import perobobbot.data.io.Platform;

@Factory
public class Converters {

    @Singleton
    public TypeConverter<String, Secret> toSecret() {
        return TypeConverter.of(String.class, Secret.class, Secret::of);
    }

    @Singleton
    public TypeConverter<Secret,String> fromSecret() {
        return TypeConverter.of(Secret.class, String.class, Secret::value);
    }

    @Singleton
    public TypeConverter<String, Platform> toPlatform() {
        return TypeConverter.of(String.class, Platform.class, Platform::new);
    }

    @Singleton
    public TypeConverter<Platform,String> fromPlatform() {
        return TypeConverter.of(Platform.class, String.class, Platform::name);
    }
}
