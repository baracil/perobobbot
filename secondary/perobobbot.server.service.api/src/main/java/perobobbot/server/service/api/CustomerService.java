package perobobbot.server.service.api;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import perobobbot.server.io.CreateCustomerParameters;
import perobobbot.server.io.view.CustomerView;

public interface CustomerService {

    @NonNull CustomerView createCustomer(@NonNull CreateCustomerParameters parameters);

    @NonNull ImmutableList<CustomerView> findCustomers(@NonNull String lastName);

}
