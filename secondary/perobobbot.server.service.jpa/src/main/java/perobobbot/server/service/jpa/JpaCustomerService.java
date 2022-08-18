package perobobbot.server.service.jpa;

import com.google.common.collect.ImmutableList;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.server.io.CreateCustomerParameters;
import perobobbot.server.io.view.CustomerView;
import perobobbot.server.service.api.CustomerService;
import perobobbot.server.service.jpa.domain.Customer;
import perobobbot.server.service.jpa.repository.CustomerRepository;

import javax.transaction.Transactional;

@Singleton
@RequiredArgsConstructor
@Transactional
public class JpaCustomerService implements CustomerService {

    private final @NonNull
    @Inject CustomerRepository customerRepository;

    @Override
    public @NonNull CustomerView createCustomer(@NonNull CreateCustomerParameters parameters) {
        final var customer = new Customer(parameters.getFirstName(), parameters.getLastName());
        return customerRepository.save(customer).toView();
    }

    @Override
    public @NonNull ImmutableList<CustomerView> findCustomers(@NonNull String lastName) {
        return customerRepository.findByLastName(lastName)
                                 .map(Customer::toView)
                                 .collect(ImmutableList.toImmutableList());
    }
}
