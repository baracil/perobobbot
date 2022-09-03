package perobobbot.data.launcher;

import fpc.tools.lang.Secret;
import io.micronaut.core.convert.ConversionContext;
import io.micronaut.core.convert.TypeConverter;
import jakarta.inject.Singleton;

import java.util.Optional;

@Singleton
public class SecretConverter implements TypeConverter<CharSequence, Secret> {

    @Override
    public Optional<Secret> convert(CharSequence object, Class<Secret> targetType, ConversionContext context) {
        return Optional.ofNullable(object).map(CharSequence::toString).map(Secret::of);
    }
}
