package perobobbot.twitch.api.serde;

import io.micronaut.context.annotation.AliasFor;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.Deserializer;
import io.micronaut.serde.Serializer;
import io.micronaut.serde.config.annotation.SerdeConfig;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Introspected
@SerdeConfig
public @interface MySerdeable {

    @AliasFor(annotation = SerdeConfig.class, member = SerdeConfig.PROPERTY)
    String property();

    @AliasFor(annotation = SerdeConfig.class, member = SerdeConfig.SERIALIZER_CLASS)
    Class<? extends Serializer> serwith() default Serializer.class;

    @AliasFor(annotation = SerdeConfig.class, member = SerdeConfig.DESERIALIZER_CLASS)
    Class<? extends Deserializer> deserwith() default Deserializer.class;

}
