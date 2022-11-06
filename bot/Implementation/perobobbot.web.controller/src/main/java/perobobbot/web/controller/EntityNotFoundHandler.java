package perobobbot.web.controller;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import jakarta.inject.Singleton;
import perobobbot.api.data.EntityNotFound;

import java.util.List;

@Produces
@Singleton
public class EntityNotFoundHandler implements ExceptionHandler<EntityNotFound, HttpResponse<EntityNotFoundHandler.Body>> {

    @Override
    public HttpResponse<Body> handle(HttpRequest request, EntityNotFound exception) {
        final var body = new Body(exception.getMessage(), exception.searchedCriteria());
        return HttpResponse.notFound(body);
    }


    @Introspected
    public record Body(@NonNull String message, @NonNull List<EntityNotFound.Criteria> searchedCriteria) {}
}
