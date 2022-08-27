package perobobbot.data.service.api;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import perobobbot.data.io.CreateCustomerParameters;
import perobobbot.data.io.view.CustomerView;

public interface CustomerService {

    @NonNull CustomerView createCustomer(@NonNull CreateCustomerParameters parameters);

    @NonNull ImmutableList<CustomerView> findCustomers(@NonNull String lastName);

}
