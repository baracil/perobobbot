package perobobbot.twitch.service.api;

import io.micronaut.aop.Around;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.METHOD})
@Around
@Inherited
public @interface AppAuth {
}
