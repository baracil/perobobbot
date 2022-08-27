package perobobbot.data.web.api;

import com.google.common.collect.ImmutableList;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import lombok.NonNull;
import perobobbot.data.io.CreateCustomerParameters;
import perobobbot.data.io.view.CustomerView;


public interface CustomerWebApi {

    String PATH = "/api/v1/customer";

    @Get("/{lastName}")
    @NonNull ImmutableList<CustomerView> getCustomers(@NonNull @PathVariable String lastName);

    @Post()
    @NonNull CustomerView createCustomer(@NonNull @Body CreateCustomerParameters parameters);
}
