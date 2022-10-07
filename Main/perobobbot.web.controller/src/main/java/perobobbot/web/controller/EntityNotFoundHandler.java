package perobobbot.web.controller;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.inject.Singleton;
import perobobbot.api.data.EntityNotFound;

@Produces
@Singleton
public class EntityNotFoundHandler implements ExceptionHandler<EntityNotFound, HttpResponse<EntityNotFoundHandler.Body>> {

    @Override
    public HttpResponse<Body> handle(HttpRequest request, EntityNotFound exception) {
        final var body = new Body(exception.searchedCriteria(), exception.searchedValue());
        return HttpResponse.notFound(body);
    }


    @Serdeable
    @Introspected
    public record Body(@NonNull String criteria, @NonNull String value) {}
}
