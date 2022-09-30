package perobobbot.launcher.converter;

import io.micronaut.core.convert.ConversionContext;
import io.micronaut.core.convert.TypeConverter;
import jakarta.inject.Singleton;

import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.Optional;

@Singleton
public class StringToPathConverter implements TypeConverter<String, Path> {

    @Override
    public Optional<Path> convert(String object, Class<Path> targetType, ConversionContext context) {
        try {
            return Optional.of(Path.of(object));
        } catch (InvalidPathException e) {
            context.reject(object,e);
            return Optional.empty();
        }
    }
}
