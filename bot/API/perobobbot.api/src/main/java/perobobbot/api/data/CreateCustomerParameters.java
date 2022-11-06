package perobobbot.api.data;

import io.micronaut.core.annotation.Introspected;
import lombok.NonNull;

@Introspected
public record CreateCustomerParameters(@NonNull String firstName, @NonNull String lastName) {

}
