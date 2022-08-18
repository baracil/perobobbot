package perobobbot.server.web.api;

import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import lombok.NonNull;
import perobobbot.server.io.CreateCustomerParameters;
import perobobbot.server.io.view.CustomerView;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface CustomerWebApi {

    @Get("/{lastName}")
    Flux<CustomerView> getCustomers(@NonNull @PathVariable String lastName);

    @Post()
    Mono<CustomerView> createCustomer(@NonNull @Body CreateCustomerParameters parameters);
}
