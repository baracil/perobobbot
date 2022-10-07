package perobobbot.api.data;

import io.micronaut.serde.annotation.Serdeable;
import lombok.NonNull;

@Serdeable
public record CreateCustomerParameters(@NonNull String firstName, @NonNull String lastName) {

}
