package perobobbot.server.io;

import lombok.NonNull;
import lombok.Value;

@Value
public class CreateCustomerParameters {

    @NonNull String firstName;
    @NonNull String lastName;
}
