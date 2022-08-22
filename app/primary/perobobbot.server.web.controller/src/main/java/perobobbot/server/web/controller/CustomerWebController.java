package perobobbot.server.web.controller;

import com.google.common.collect.ImmutableList;
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

@OpenAPIDefinition(
        info = @Info(
                title = "perobobbot",
                version = "0.0"
        )
)
@Controller(value = "/api/v1/customer")
@RequiredArgsConstructor
public class CustomerWebController implements CustomerWebApi {

    private final @NonNull
    @Inject CustomerService customerService;

    @Override
    public @NonNull ImmutableList<CustomerView> getCustomers(@NonNull String lastName) {
        return customerService.findCustomers(lastName);
    }

    @Override
    public @NonNull CustomerView createCustomer(@NonNull CreateCustomerParameters parameters) {
        return customerService.createCustomer(parameters);
    }
}
