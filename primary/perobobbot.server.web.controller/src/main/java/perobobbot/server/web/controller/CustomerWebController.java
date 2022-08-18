package perobobbot.server.web.controller;

import io.micronaut.http.annotation.Controller;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import jakarta.inject.Inject;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.server.io.CreateCustomerParameters;
import perobobbot.server.io.view.CustomerView;
import perobobbot.server.service.api.CustomerService;
import perobobbot.server.web.api.CustomerWebApi;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@OpenAPIDefinition(
        info = @Info(
                title = "perobobbot",
                version = "0.0"
        )
)
@Controller(value = "/customer")
@RequiredArgsConstructor
public class CustomerWebController implements CustomerWebApi {

    private final @NonNull
    @Inject CustomerService customerService;

    @Override
    public Flux<CustomerView> getCustomers(@NonNull String lastName) {
        return Mono.fromCallable(() -> customerService.findCustomers(lastName))
                   .flatMapMany(Flux::fromIterable)
                   .subscribeOn(TaskSchedulers.DB);
    }

    @Override
    public Mono<CustomerView> createCustomer(@NonNull CreateCustomerParameters parameters) {
        return Mono.fromCallable(() -> customerService.createCustomer(parameters))
                   .subscribeOn(TaskSchedulers.DB);
    }
}
