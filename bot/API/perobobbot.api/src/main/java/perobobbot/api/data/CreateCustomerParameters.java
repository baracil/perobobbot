package perobobbot.api.data;

import io.micronaut.core.annotation.Introspected;

@Introspected
public record CreateCustomerParameters(String firstName, String lastName) {

}
