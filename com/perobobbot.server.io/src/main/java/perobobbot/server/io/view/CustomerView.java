package perobobbot.server.io.view;

import lombok.NonNull;
import lombok.Value;

@Value
public class CustomerView {

    @NonNull Long id;
    @NonNull String firstName;
    @NonNull String lastName;

}
