package perobobbot.data.io;

import lombok.NonNull;

public record CreateCustomerParameters(@NonNull String firstName, @NonNull String lastName) {

}
