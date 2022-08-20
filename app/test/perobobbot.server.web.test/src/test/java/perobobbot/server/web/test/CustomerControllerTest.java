package perobobbot.server.web.test;

import com.google.common.collect.ImmutableList;
import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.annotation.MockBean;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import lombok.NonNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import perobobbot.server.io.view.CustomerView;
import perobobbot.server.service.api.CustomerService;

@MicronautTest
public class CustomerControllerTest {

    @Inject
    public CustomerService customerService;

    @Inject
    @Client("/")
    public HttpClient client;

    private static final ImmutableList<CustomerView> EXPECTED_CUSTOMERS = ImmutableList.of(new CustomerView(0L, "Frank", "Dupont"));

    private void prepareMock() {
        Mockito.when(customerService.findCustomers("Dupont"))
               .thenReturn(EXPECTED_CUSTOMERS);

        Mockito.when(customerService.findCustomers("Dupond"))
               .thenReturn(ImmutableList.of());


    }

    @Test
    public void name() {
        prepareMock();

        final var response = client.toBlocking()
                                   .exchange(HttpRequest.GET("/v1/customer/Dupont"), Argument.listOf(CustomerView.class));

        final var body = response.body();

        Assertions.assertEquals(200, response.code());
        Assertions.assertNotNull(body, "Body should not be null");
        Assertions.assertEquals(EXPECTED_CUSTOMERS, body);
    }

    @MockBean
    public @NonNull CustomerService customerService() {
        return Mockito.mock(CustomerService.class);
    }

}
