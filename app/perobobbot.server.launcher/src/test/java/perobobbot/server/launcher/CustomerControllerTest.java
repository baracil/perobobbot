package perobobbot.server.launcher;

import com.google.common.collect.ImmutableList;
import io.micronaut.context.annotation.Primary;
import io.micronaut.test.annotation.MockBean;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import lombok.NonNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import perobobbot.server.io.view.CustomerView;
import perobobbot.server.service.api.CustomerService;

@MicronautTest
public class CustomerControllerTest {

    @Inject
    TestCustomerClient client;

    @Inject
    CustomerService customerService;


    @Test
    void name() {
        final var expectedCustomers = ImmutableList.of(new CustomerView(0L, "Frank", "Dupont"));

        Mockito.when(customerService.findCustomers("Dupont")).thenAnswer((Answer<ImmutableList<CustomerView>>) invocationOnMock -> expectedCustomers);

        Mockito.when(customerService.findCustomers("Dupont"))
               .thenReturn(expectedCustomers);

        final var actualCustomers = client.getCustomers("Dupont").collectList().block();

        Assertions.assertAll(
                () -> Assertions.assertNotNull(actualCustomers),
                () -> Assertions.assertEquals(expectedCustomers, actualCustomers)
        );
    }

    @MockBean
    @Primary
    public @NonNull CustomerService customerService() {
        return Mockito.mock(CustomerService.class);
    }

}
