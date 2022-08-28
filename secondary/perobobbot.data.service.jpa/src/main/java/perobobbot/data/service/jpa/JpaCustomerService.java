package perobobbot.data.service.jpa;

import com.google.common.collect.ImmutableList;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.data.io.CreateCustomerParameters;
import perobobbot.data.io.view.CustomerView;
import perobobbot.data.service.api.CustomerService;
import perobobbot.data.service.jpa.domain.Customer;
import perobobbot.data.service.jpa.repository.CustomerRepository;

import javax.transaction.Transactional;

@Singleton
@RequiredArgsConstructor
@Transactional
public class JpaCustomerService implements CustomerService {

    private final @NonNull
    @Inject CustomerRepository customerRepository;

    @Override
    public @NonNull CustomerView createCustomer(@NonNull CreateCustomerParameters parameters) {
        final var customer = new Customer(parameters.firstName(), parameters.lastName());
        return customerRepository.save(customer).toView();
    }

    @Override
    public @NonNull ImmutableList<CustomerView> findCustomers(@NonNull String lastName) {
        return customerRepository.findByLastName(lastName).stream()
                                 .map(Customer::toView)
                                 .collect(ImmutableList.toImmutableList());
    }
}
